package com.palmergames.bukkit.towny.object;



import com.nijikokun.register.payment.Method;
import com.palmergames.bukkit.towny.EconomyException;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.util.EconomyTools;

public class TownyEconomyObject extends TownyObject {
	private static Towny plugin;

	public static Towny getPlugin() {
		return plugin;
	}

	public static void setPlugin(Towny plugin) {
		TownyEconomyObject.plugin = plugin;
	}
        
        /*Tries to pay from the players main bank account first, if it fails try their holdings */
	public boolean pay(double n) throws EconomyException
        {
	        if(canPayFromHoldings(n))
	        {
	        	plugin.sendDebugMsg("Can Pay: " + n);
	            getEconomyAccount().subtract(n);
	            return true;
	        }
		return false;
	}

        /* When collecting money add it to the Accounts bank */
	public void collect(double n) throws EconomyException
        {
		getEconomyAccount().add(n);
	}

        /*When one account is paying another account(Taxes/Plot Purchasing)*/
	public boolean pay(double n, TownyEconomyObject collector) throws EconomyException {
		if (pay(n)) {
			collector.collect(n);
			return true;
		} else
			return false;
	}

	public String getEconomyName() {
		// TODO: Make this less hard coded.
		if (this instanceof Nation)
			return "nation-" + getName();
		else if (this instanceof Town)
			return "town-" + getName();
		else
			return getName();
	}

	/*
	public double getBankBalance() throws EconomyException
	{
		try
		{
			checkEconomy();
			return EconomyTools.getMethod().getAccount(getEconomyName()).getBankHoldings(0).balance();
		}
		catch(NoClassDefFoundError e)
		{
			e.printStackTrace();
			throw new EconomyException("Economy error getting balance for " + getEconomyName());
		}
	}
	*/
	public double getHoldingBalance() throws EconomyException
	{
		try
		{
			plugin.sendDebugMsg("Economy Balance Name: " + getEconomyName());
			Method.MethodAccount account = getEconomyAccount();
			if(account!=null)
			return EconomyTools.getMethod().getAccount(getEconomyName()).balance();
			else
			{
			plugin.sendDebugMsg("Account is still null!");
			return 0;
			}
		}
		catch(NoClassDefFoundError e)
		{
			e.printStackTrace();
			throw new EconomyException("Economy error getting holdings for " + getEconomyName());
		}
	}
	
        /*Gets the Players bank account, if it does not exist it creates one and makes it their main*/
	/*
	public BankAccount getEconomyBankAccount() throws EconomyException
	{
            try
            {
                BankAccount baccount = iConomy.getAccount(getEconomyName()).getMainBankAccount();
                if(baccount!=null)
                {
                	plugin.sendDebugMsg("Got Bank Account: " + baccount.toString());
                    return baccount;
                }
                else
                {
                	plugin.sendDebugMsg("Creating Bank Account");
                    Bank bank = iConomy.getBank(getEconomyName());
                    int count = iConomy.Banks.count(getEconomyName());
                    //plugin.sendDebugMsg("Bank: " + bank.toString());
                    if(bank==null)
                    {
                    	plugin.sendDebugMsg("Bank was Null");
                    	String test = getEconomyName();
                    	plugin.sendDebugMsg(test);
                        bank.createAccount(getEconomyName());
                        plugin.sendDebugMsg("Making Account: " + getEconomyName());
                        if (count == 0)
                        {
                        	plugin.sendDebugMsg("Player has 0 Accounts, Making this their main");
                            iConomy.getAccount(getEconomyName()).setMainBank(bank.getId());
                        }
                    }
                    return iConomy.getAccount(getEconomyName()).getMainBankAccount();
                }
            }
                    
            catch(NoClassDefFoundError e)
            {
                    e.printStackTrace();
                    throw new EconomyException("IConomy error getting Bank: " + getEconomyName());
            }
	}
	*/

	public Method.MethodAccount getEconomyAccount() throws EconomyException
	{
		try 
		{
			if(!EconomyTools.getMethod().hasAccount(getEconomyName()));
			{
				Method.MethodAccount account = EconomyTools.getMethod().getAccount(getEconomyName());
				if(account!=null)
				{
					return account;
				}
			}
			return null;
		} 
		catch (NoClassDefFoundError e) 
		{
			e.printStackTrace();
			throw new EconomyException("Economy error. Incorrect install.");
		}
		
	}
	
	public boolean canPayFromHoldings(double n) throws EconomyException
	{
		if(getHoldingBalance()-n>=0)
			return true;
		else
			return false;
	}

	/*
	public boolean canPayFromBank(double n) throws EconomyException
	{
		if(getIConomyBankAccount().getHoldings().hasEnough(getBankBalance() - n ))
			return true;
		else
			return false;
	}
	*/
	
	/* Used To Get Balance of Players holdings in String format for printing*/
	public String getHoldingFormattedBalance() {
		return null;
	}
	
	
	/* Used To Get Balance of Players Bank in String format for printing*/
	/*
	@SuppressWarnings("static-access")
	public String getBankFormattedBalance() {
		try {
			return iConomy.format(getBankBalance());
		} catch (EconomyException e) {
			return "0 " + getIConomyCurrency();
		}
	}
	*/
}
