package de.ait.tp.repositories;

import de.ait.tp.models.Answer;
import de.ait.tp.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@Component
public interface AnswersRepository extends JpaRepository<Answer,Long> {

    List<Answer> findAnswersByQuestionId(Long randomId);
    Optional<Answer> findByQuestionAndId(Question question, Long answerId);

    List<Answer> findAllById(Long selectedAnswerId);

    boolean existsByAnswerAndQuestionId(String answer, Long questionId);

}
