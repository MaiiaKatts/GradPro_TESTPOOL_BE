package de.ait.tp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Schema(name = "UpdateUser",description = "Name,types or level update " )

public class UpdateUserDto {
    @Schema(description = "User firstname", example = "Kristina")
    private String firstName;
    @Schema(description = "User lastname", example = "Romanova")
    private String lastName;

}
