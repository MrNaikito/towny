package com.palmergames.bukkit.towny.object;


import com.iConomy.iConomy;
import com.iConomy.system.Account;
import com.palmergames.bukkit.towny.EconomyException;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownySettings;
import com.nijikokun.register.payment.Method.MethodAccount;
import com.nijikokun.register.payment.Methods;

public class TownyEconomyObject extends TownyObject {
	
	private static Towny plugin;

    public static void setPlugin(Towny plugin) {
    	TownyEconomyObject.plugin = plugin;
    }
        
    /*Tries to pay from the players main bank account first, if it fails try their holdings */
    public boolean pay(double n) throws EconomyException 
    {
            if(canPayFromHoldings(n))
            {
                plugin.sendDebugMsg("Can Pay: " + n);
                if(TownySettings.isUsingRegister())
                	((MethodAccount) getEconomyAccount()).subtract(n);
                else
                	((Account) getEconomyAccount()).getHoldings().subtract(n);
                return true;
            }
            return false;
     }

        /* When collecting money add it to the Accounts bank */
        public void collect(double n) throws EconomyException 
        {
        	if(TownySettings.isUsingRegister())
        		((MethodAccount) getEconomyAccount()).add(n);
        	else
        		((Account) getEconomyAccount()).getHoldings().add(n);
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

        public void setBalance(double value) 
        {
        	try
            {            	
            	if(TownySettings.isUsingRegister()) {
            		MethodAccount account = (MethodAccount)getEconomyAccount();
            		if(account!=null) {
                        account.set(value);                        
                    }
                    else
                    {
                        plugin.sendDebugMsg("Account is still null!");
                    }
            	} else {
            		Account account = (Account)getEconomyAccount();
            		if(account!=null) {
                        account.getHoldings().set(value);
                    }
                    else
                    {
                        plugin.sendDebugMsg("Account is still null!");
                    }
            	}

                
            }
            catch(NoClassDefFoundError e)
            {
                e.printStackTrace();
                plugin.sendDebugMsg("Economy error getting holdings from " + getEconomyName());
            } catch (EconomyException e) {
            	e.printStackTrace();
            	plugin.sendDebugMsg("Economy error getting Account for " + getEconomyName());
			}
        }
        
        public double getHoldingBalance() throws EconomyException
        {
                try
                {
                	plugin.sendDebugMsg("Economy Balance Name: " + getEconomyName());
                	
                	if(TownySettings.isUsingRegister()) {
                		MethodAccount account = (MethodAccount)getEconomyAccount();
                		if(account!=null) {
                        	plugin.sendDebugMsg("Economy Balance: " + account.balance());
                            return account.balance() ;
                        }
                        else
                        {
                            plugin.sendDebugMsg("Account is still null!");
                            return 0;
                        }
                	} else {
                		Account account = (Account)getEconomyAccount();
                		if(account!=null) {
                        	plugin.sendDebugMsg("Economy Balance: " + account.getHoldings().balance());
                            return account.getHoldings().balance();
                        }
                        else
                        {
                            plugin.sendDebugMsg("Account is still null!");
                            return 0;
                        }
                	} 
                }
                catch(NoClassDefFoundError e)
                {
                        e.printStackTrace();
                        throw new EconomyException("Economy error getting holdings for " + getEconomyName());
                }
        }

        public Object getEconomyAccount() throws EconomyException 
        {
        	try 
            {
            	if(TownySettings.isUsingRegister()) { 
            		
            		if (Methods.getMethod().getAccount(getEconomyName()) == null)
            			Methods.getMethod().createAccount(getEconomyName());
            		
            		return Methods.getMethod().getAccount(getEconomyName());
            		
            	} else if(TownySettings.isUsingIConomy()){
                            return iConomy.getAccount(getEconomyName());
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
        
        public static void checkEconomy() throws EconomyException {
                
                if(TownySettings.isUsingRegister()) {
                	return;
                } else if(TownySettings.isUsingIConomy()){
                	return;
                } else
                	throw new EconomyException("No Economy plugins are configured.");
        }
        
        public static String getEconomyCurrency() {
        	if(TownySettings.isUsingRegister()) {
        		return Methods.getMethod().format(0).split(" ")[1];
        	} else if(TownySettings.isUsingIConomy()){
        		return iConomy.format(0).split(" ")[1];
        	}
        	return ""; 
        }
        
        /* Used To Get Balance of Players holdings in String format for printing*/
		public String getHoldingFormattedBalance() {
			try {
				double balance = getHoldingBalance();
				try {
					if(TownySettings.isUsingRegister()) {
						return Methods.getMethod().format(balance);
					} else if(TownySettings.isUsingIConomy()){
						return iConomy.format(balance);
					}
					
				} catch (Exception eInvalidAPIFunction) {
				}
				return String.format("%.2f", balance);
			} catch (EconomyException eNoIconomy) {
			        return "Error Accessing Bank Account";
			}
		}
		
		public static String getFormattedBalance(double balance) {

			try {
				if(TownySettings.isUsingRegister()) {
					return Methods.getMethod().format(balance);
				} else if(TownySettings.isUsingIConomy()){
					return iConomy.format(balance);
				}
				
			} catch (Exception eInvalidAPIFunction) {
			}
			return String.format("%.2f", balance);
		}
}
