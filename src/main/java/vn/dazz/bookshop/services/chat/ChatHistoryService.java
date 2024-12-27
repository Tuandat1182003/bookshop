package vn.dazz.bookshop.services.chat;

import vn.dazz.bookshop.entities.ChatHistory;

public interface ChatHistoryService {
    void insertIntoChatHistory(String content, Integer possess);
}
