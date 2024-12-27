package vn.dazz.bookshop.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.dazz.bookshop.entities.ChatHistory;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO chat_history(content, prossess) values (:content, :possess)", nativeQuery = true)
    void chatInsert(String content, Integer possess);
}
