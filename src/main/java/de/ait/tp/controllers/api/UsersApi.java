package de.ait.tp.controllers.api;

import de.ait.tp.dto.NewUserDto;
import de.ait.tp.dto.StandardResponseDto;
import de.ait.tp.dto.UserDto;
import de.ait.tp.security.details.AuthenticatedUser;
import de.ait.tp.validation.dto.ValidationErrorsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tags(@Tag(name = "Users"))
@RequestMapping("/api/users")

public interface UsersApi {

    @Operation(summary = "User registration", description = "Available for all users. Default- USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User is registered",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Validation error ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorsDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "An account with this email already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    UserDto register(@RequestBody @Valid NewUserDto newUser) ;



    @Operation(summary = "Confirmation of registration", description = "Available to registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Registration confirmed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "User is not found or the verification code is no longer valid",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @GetMapping("/confirm/{confirm-code}")
    UserDto getConfirmation(@PathVariable("confirm-code") String confirmCode);

    @Operation(summary = "Get user data", description = "Available to registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Request processed successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "User is not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "User unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })

    @GetMapping("/profile")
    UserDto getProfile(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user);

    }
