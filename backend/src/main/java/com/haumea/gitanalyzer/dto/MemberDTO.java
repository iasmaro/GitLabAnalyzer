package com.haumea.gitanalyzer.dto;

import java.util.List;

public class MemberDTO {
    private String memberId;
    private List<String> alias;

    public MemberDTO(String memberId, List<String> alias) {
        this.memberId = memberId;
        this.alias = alias;
    }
}
