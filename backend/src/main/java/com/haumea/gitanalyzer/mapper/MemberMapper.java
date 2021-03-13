package com.haumea.gitanalyzer.mapper;

import com.haumea.gitanalyzer.dto.MemberDTO;
import com.haumea.gitanalyzer.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    @Mapping(source = "memberId", target = "memberId")
    @Mapping(source = "alias", target = "alias")
    MemberDTO toDTO(Member member);

    List<MemberDTO> toDTOs(List<Member> members);
}


