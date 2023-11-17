package de.ait.tp.service;

import de.ait.tp.dto.TestResultDto;
import de.ait.tp.dto.TestTotalResultDto;
import de.ait.tp.models.TestResult;

import java.util.List;

public interface TestResultService {
    int calculateCorrectAnswers(Long userId, Long testId, List<Long> userAnswers);
    TestTotalResultDto calculateTotalCorrectAnswers(Long userId);
    TestTotalResultDto calculateCorrectAnswersAndSum(Long userId, Long testId, List<Long> userAnswers);
    List<TestResultDto> getTestResultsForUser(Long userId);
}