package dev.sebastianb.ffactions.permission;

public enum FactionMemberRanks {
    LEADER(null),
    OFFICER(null),
    MEMBER(OFFICER),
    VISITOR(MEMBER);

    private final FactionMemberRanks rankParent;

    FactionMemberRanks(FactionMemberRanks ranks) {
        this.rankParent = ranks;
    }

    public FactionMemberRanks getRankParent() {
        return rankParent;
    }
}
