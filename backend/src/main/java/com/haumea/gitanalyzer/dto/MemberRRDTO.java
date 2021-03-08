package com.haumea.gitanalyzer.dto;

import java.util.List;

public class MemberRRDTO {
    private List<String> members;
    private List<String> aliases;

    public MemberRRDTO(List<String> members, List<String> aliases) {
        this.members = members;
        this.aliases = aliases;
    }

    public List<String> getMembers() {
        return members;
    }

    public List<String> getAliases() {
        return aliases;
    }
}
