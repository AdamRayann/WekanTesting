package integrationData;

public class Accounts {

    private Account[] accounts;

    public Accounts() {
        this.accounts = new Account[]{new Account("admin1", "test@example.com", "admin1")};

    }

    public Accounts(Account[] accounts) {
        this.accounts = accounts;
    }

    public Account[] getAccounts() {
        return accounts.clone();
    }


    public static class Account {
        private String userName;
        private String email;
        private String password;

        public Account(String userName, String email, String password) {
            this.userName = userName;
            this.email = email;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "UserName='" + userName + '\'' +
                    ", Email='" + email + '\'' +
                    ", Password=" + password +
                    '}';
        }
    }

    public static final Account account1 = new Account("admin1", "test@example.com", "admin1");

}