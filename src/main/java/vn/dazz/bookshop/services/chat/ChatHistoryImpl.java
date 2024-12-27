package vn.dazz.bookshop.services.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dazz.bookshop.repositories.ChatHistoryRepository;

@Service
public class ChatHistoryImpl implements ChatHistoryService {
    @Autowired
    ChatHistoryRepository chatHistoryRepository;

    @Override
    public void insertIntoChatHistory(String content, Integer possess) {
        chatHistoryRepository.chatInsert(content, possess);
    }

}
