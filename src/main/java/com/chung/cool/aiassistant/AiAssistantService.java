package com.chung.cool.aiassistant;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class: AiAssistantService
 * Author: Chung Ha
 * Date: 2023-03-06
 * Description: This service class handles the embedding storage and conversational retrieval for the AI assistant.
 */
@Service
@Slf4j
public class AiAssistantService {
    private final EmbeddingStore<TextSegment> elasticsearchEmbeddingStore;
    private final EmbeddingModel embeddingModel;
    private final ConversationalRetrievalChain conversationalRetrievalChain;

    @Autowired
    private ChatMemoryRepository chatMemoryRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;
    public AiAssistantService(EmbeddingModel embeddingModel,
                              EmbeddingStore<TextSegment> elasticsearchEmbeddingStore,
                              ConversationalRetrievalChain conversationalRetrievalChain) {
        this.embeddingModel = embeddingModel;
        this.elasticsearchEmbeddingStore = elasticsearchEmbeddingStore;
        this.conversationalRetrievalChain = conversationalRetrievalChain;
    }

    public void embeddingStore(String fileName) {
        log.info("embeddingStore called---------------");
        //log.info("uploadDir+fileName: "+uploadDir+fileName);
        log.info("uploadDir+fileName: "+uploadDir+fileName);
        try {
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            List<TextSegment> documentSegments=null;
            Document document=null;
            if ("pdf".equals(fileType)) {
                document = FileSystemDocumentLoader.loadDocument(Paths.get(uploadDir + fileName),
                        new ApachePdfBoxDocumentParser());

            } else if ("txt".equals(fileType)) {
                document = FileSystemDocumentLoader.loadDocument(Paths.get(uploadDir + fileName),
                        new TextDocumentParser());
            }
            DocumentSplitter splitter = DocumentSplitters.recursive(200, 0);
            documentSegments = splitter.split(document);
            List<Embedding> documentEmbeddings = embeddingModel.embedAll(documentSegments).content();
            elasticsearchEmbeddingStore.addAll(documentEmbeddings, documentSegments);
        }catch (Exception e){
            log.error("Error in embeddingStore: "+e.getMessage());
        }

        log.info("embeddingStore completed---------------");
    }

    public String chat(String prompt) {
        return conversationalRetrievalChain.execute(prompt);
    }

    public String getChatMemory(Long userId) {
        return chatMemoryRepository.findById(userId).orElse(new ChatMemory()).getMessage();
    }

}
