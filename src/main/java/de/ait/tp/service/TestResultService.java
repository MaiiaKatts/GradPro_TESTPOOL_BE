package de.ait.tp.service;

import de.ait.tp.models.TestResult;

import java.util.List;

public interface TestResultService {
    int calculateCorrectAnswers(Long userId, Long testId, List<Long> userAnswers);
    int calculateTotalCorrectAnswers(Long userId);
    int calculateCorrectAnswersAndSum(Long userId, Long testId, List<Long> userAnswers);
    List<TestResult> getTestResultsForUser(Long userId);
}