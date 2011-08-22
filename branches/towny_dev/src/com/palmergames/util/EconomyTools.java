package com.palmergames.util;

import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Methods;
import org.bukkit.plugin.Plugin;

public class EconomyTools
{
    private static Methods methods = new Methods();

    public static void Initialize(Plugin p)
    {
        if(methods.hasMethod()) return;
        if(!methods.setMethod(p)) return;
        System.out.println("[Towny] Using: " + methods.getMethod().getName());
    }

    public static Boolean isUsingEconomy()
    {
        return methods.hasMethod();
    }

    public static Method getMethod()
    {
        return methods.getMethod();
    }

    public static String Format(Double cost)
    {
        return methods.getMethod().format(cost);
    }
}
