public class Account
{
    private volatile Long money;
    private volatile boolean blocked;

    private String accNumber;
    private String status;

    protected Account(String accNumber, Long money) {
        this.money = money;
        this.accNumber = accNumber;
    }

    protected long getMoney() {
        return money;
    }

    protected void setMoney(long money) {
        this.money = money;
    }

    protected String getAccNumber() {
        return accNumber;
    }

    protected boolean isBlocked() {
        return blocked;
    }

    protected void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    protected String getStatus() {
        return isBlocked() ? "blocked" : "not blocked";
    }
}
