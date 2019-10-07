public class Account
{
    private Long money;
    private String accNumber;
    private boolean blocked;
    private String status;
    private boolean exist = false;

    protected Account(String accNumber, Long money) {
        this.money = money;
        this.accNumber = accNumber;
        exist = true;
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

    protected boolean isExist() {
        return exist;
    }

}
