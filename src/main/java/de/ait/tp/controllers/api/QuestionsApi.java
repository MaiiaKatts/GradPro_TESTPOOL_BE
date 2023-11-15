package de.ait.tp.controllers.api;

import de.ait.tp.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

        @ApiResponse(responseCode = "401",
                description = "Admin unauthorized",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = StandardResponseDto.class)))

@Tags(value=@Tag(name = "Questions"))
@Schema(name = "Question", description = "Questions")

public interface QuestionsApi {

    @Operation(summary = "Adding a question", description = "Available to admin ")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Question added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Question not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "A question in this test already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only admin available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))

})
    @PostMapping("/api/tests/{test_id}/questions")
    @ResponseStatus(HttpStatus.CREATED)
    QuestionDto addQuestionToTest(@PathVariable("test_id") Long testId,
            @RequestBody @Valid NewQuestionDto newQuestion);

    @Operation(summary = "Receiving 10 questions with answers selectively from a given list ",
            description = "Available to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Questions not found ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only user available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @GetMapping("/api/tests/{test_id}/questions/randomQuestions")
    List<QuestionWithAnswersDto> getAllQuestionIds(@PathVariable("test_id") Long testId);

    @Operation(summary = "Receiving all questions", description = "Available to admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Questions not found ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only admin available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @GetMapping("/api/questions")
    List<QuestionDto> getAllQuestions();

    @Operation(summary = "Question update", description = "Available to admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Question not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "The request was made incorrectly",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "A question in this test already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only admin available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @PutMapping("/api/tests/{test_id}/questions/{question_id}")
    QuestionDto updateQuestionInTest(@PathVariable("test_id") Long testId,
                                   @PathVariable("question_id") Long questionId,
                                   @RequestBody @Valid UpdateQuestionDto updateQuestion);

    @Operation(summary = "Delete Question", description = "Valid after deleting four answers." +
            "Available to admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Request processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDto.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Question not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden, only admin available",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @DeleteMapping("/api/tests/{test_id}/questions/{question_id}")
    QuestionDto deleteQuestionFromTest(@PathVariable("question_id") Long questionId);

}
