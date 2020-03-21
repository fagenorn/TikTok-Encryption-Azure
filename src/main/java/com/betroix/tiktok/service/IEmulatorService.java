package com.betroix.tiktok.service;

public interface IEmulatorService {
    public byte[] ttEncrypt(byte[] x);

    public byte[] leviathan(int time, byte[] x);
}
