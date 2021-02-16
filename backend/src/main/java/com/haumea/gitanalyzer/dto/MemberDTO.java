package com.haumea.gitanalyzer.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class MemberDTO {
    @NotBlank
    private String memberId;
    private List<String> alias;

    public MemberDTO(String memberId, List<String> alias) {
        this.memberId = memberId;
        this.alias = alias;
    }

    public String getMemberId() {
        return memberId;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }
}
