package dev.sebastianb.ffactions.permission;

import dev.sebastianb.ffactions.FFactions;

public enum FactionMemberRanks {
    LEADER(5), // skipped a number because I don't want someone to be promoted to "leader". Always keep a gap for new enum values
    OFFICER(3),
    MEMBER(2),
    VISITOR(1);

    private final int rankWeight;

    FactionMemberRanks(int weight) {
        this.rankWeight = weight;
    }

    public FactionMemberRanks getParentRank(FactionMemberRanks rank) {
        return getRelativeRank(rank, 1);
    }

    public FactionMemberRanks getChildRank(FactionMemberRanks rank) {
        return getRelativeRank(rank, -1);
    }

    private FactionMemberRanks getRelativeRank(FactionMemberRanks rank, int relRankNum) {
        FactionMemberRanks relRank = null;
        int i = 0;
        try {
            for (FactionMemberRanks currentRank : FactionMemberRanks.values()) {
                if (currentRank.getRankWeight() + relRankNum == rank.getRankWeight()) {
                    relRank = currentRank;
                    i++;
                }
                if (i > 1) {
                    throw new Exception("Calling multiple parents and/or children for faction members. This should not happen please report.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (relRank == null) {
            FFactions.LOGGER.info("No child or parent of " + rank.name()); // TODO: Replace with translatable
        }
        return relRank;
    }

    private int getRankWeight() {
        return rankWeight;
    }

}
