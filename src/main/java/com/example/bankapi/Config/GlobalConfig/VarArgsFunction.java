package com.example.bankapi.Config.GlobalConfig;

public interface VarArgsFunction <T,R>{
    R apply (T ... args);
}
