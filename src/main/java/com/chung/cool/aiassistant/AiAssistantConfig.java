package com.chung.cool.aiassistant;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AiAssistantConfig {

    @Bean
    public EmbeddingModel embeddingModel() {
        log.info("embeddingModel instantiated---------------");
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    public EmbeddingStore<TextSegment> elasticSearchEmbeddingStore(){
        log.info("elasticSearchEmbeddingStore instantiated---------------");
        EmbeddingStore<TextSegment> embeddingStore = ElasticsearchEmbeddingStore.builder()
                .serverUrl("http://localhost:9200")
                .dimension(384)
                .build();
        return embeddingStore;
    }

    @Bean
    public ConversationalRetrievalChain mistralConversationalRetrievalChain(){
        MistralAiChatModel mistralAiChatModel =  MistralAiChatModel.builder().baseUrl("http://localhost:1234/v1")
                .apiKey("lm-studio")
                .build();

        return ConversationalRetrievalChain.builder()
                .chatLanguageModel(mistralAiChatModel)
                .retriever(EmbeddingStoreRetriever.from(elasticSearchEmbeddingStore(),
                        embeddingModel()))
                .build();
    }
}
