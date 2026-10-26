package org.sopt.member.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateMemberRequest (
		@NotBlank String name
) {
}
