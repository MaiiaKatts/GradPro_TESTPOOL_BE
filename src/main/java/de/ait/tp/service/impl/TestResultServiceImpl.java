package de.ait.tp.service.impl;

import de.ait.tp.dto.TestResultDto;
import de.ait.tp.dto.TestTotalResultDto;
import de.ait.tp.models.*;
import de.ait.tp.repositories.TestResultRepository;
import de.ait.tp.repositories.TestTotalResultRepository;
import de.ait.tp.repositories.TestsRepository;
import de.ait.tp.repositories.UsersRepository;
import de.ait.tp.service.AnswersService;
import de.ait.tp.service.TestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import static de.ait.tp.dto.TestTotalResultDto.from;

@RequiredArgsConstructor
@Service
@Component
public class TestResultServiceImpl implements TestResultService {

   @Value("${myapp.maxPoints}")
    private int maxPoints;

    private final TestResultRepository testResultRepository;
    private final TestTotalResultRepository testTotalResultRepository;
    private final UsersRepository userRepository;
    private final TestsRepository testRepository;
    private final AnswersService answersService;

    @Override

    public TestTotalResultDto calculateCorrectAnswersAndSum(Long userId, Long testId, List<Long> userAnswers) {
      //  int maxPoints = 3;
        TestResult previousResult = testResultRepository.findTopByUserIdAndTestIdOrderByIdDesc(userId, testId);


        int newCorrectAnswersCount = calculateCorrectAnswers(userId, testId, userAnswers);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        TestResult testResult = new TestResult();
        testResult.setUser(user);
        testResult.setTest(test);
        testResult.setScore(newCorrectAnswersCount);
        testResult.setDate(LocalDate.now());


        if (previousResult != null) {
            double previousProgress = previousResult.getProgressPercent();
            double progressIncrement = ((double) newCorrectAnswersCount / maxPoints) * 100 - previousProgress;
            double progressPercent = previousProgress + progressIncrement;
            testResult.setProgressPercent(progressPercent);
        } else {


            double progressPercent = ((double) newCorrectAnswersCount / maxPoints) * 100;
            testResult.setProgressPercent(progressPercent);
        }

        testResultRepository.save(testResult);
        return calculateTotalCorrectAnswers(userId);

    }

    @Override
    public List<TestResultDto> getTestResultsForUser(Long userId) {
        return testResultRepository.findAllByUserId(userId);
    }

    public List<TestTotalResult> getAllUsersTotalCorrectAnswers() {
        return testTotalResultRepository.findAllByOrderByTotalCorrectAnswerDesc();
    }

    @Override
    public  TestTotalResultDto calculateTotalCorrectAnswers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TestResult> testResults = testResultRepository.findByUserId(userId);
        int totalCorrectAnswers = 0;
        int totalTestsTaken = testResults.size();
        for (TestResult result : testResults) {
            totalCorrectAnswers += result.getScore();
        }
        List<TestTotalResult> testTotalResults = testTotalResultRepository
                .findByUserIdOrderByTotalCorrectAnswerDesc(userId);
        TestTotalResult totalResult;
        if (testTotalResults.isEmpty()) {
            totalResult = new TestTotalResult();
            totalResult.setUser(user);

        } else {
            totalResult = testTotalResults.get(0);
        }
        totalResult.setTotalCorrectAnswer(totalCorrectAnswers);
        totalResult.setTestTaken(totalTestsTaken);
        testTotalResultRepository.save(totalResult);

        return TestTotalResultDto.from(totalResult);
    }

    @Override
    public int calculateCorrectAnswers(Long userId, Long testId, List<Long> userAnswers) {
        int correctAnswersCount = 0;
        for (Long selectedAnswerId : userAnswers) {
            boolean isCorrect = answersService.getCorrectAnswer(selectedAnswerId);
            if (isCorrect) {
                correctAnswersCount++;
            }
        }
        return correctAnswersCount;
    }
}
