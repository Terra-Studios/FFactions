package dev.sebastianb.ffactions.admin;

public enum StorageSystem {

    H2,
    NOSQL;

    public static StorageSystem storageSystem = H2; // TODO: make a actual config system for storage method

}
