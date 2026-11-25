package org.sopt.member.dto;

import org.sopt.member.domain.MemberRole;

public record MemberList(Long id, String name, MemberRole role) {
}