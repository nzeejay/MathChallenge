public class Question {
    public float Number1;
    public int Operator;
    public float Number2;
    public float Answer;

    //operators
    public static final int ADD = 0;
    public static final int SUB = 1;
    public static final int MUL = 2;
    public static final int DIV = 3;

    public Question(float num1, int op, float num2, float ans) {
        this.Number1 = num1;
        this.Number2 = num2;
        this.Operator = op;
        this.Answer = ans;
    }

    public Question(String str) throws Exception {
        String temp = "";

        char[] strArr = str.toCharArray();

        //remove whitespace
        for (int i  = 0; i < strArr.length; i++) {
            if(strArr[i] != ' ')
                temp += strArr[i];
        }

        strArr = temp.toCharArray();

        String num1 = "";
        char op = ' ';
        String num2 = "";
        String ans = "";

        int state = 0;

        for (int i  = 0; i < strArr.length; i++) {
            switch(state)
            {
                case(0):
                    if(strArr[i] == '+' || strArr[i] == '-' || strArr[i] == '*' || strArr[i] == '/') {
                        op = strArr[i];
                        state = 1;
                        continue;
                    }

                    num1 += strArr[i];
                    break;
                case(1):
                    if(strArr[i] == '+' || strArr[i] == '-' || strArr[i] == '*' || strArr[i] == '/')
                        throw new Exception("Extra Operator");

                    if(strArr[i] == '=') {
                        state = 2;
                        continue;
                    }

                    num2 += strArr[i];
                    break;
                case(2):
                    ans += strArr[i];
                    break;
            }

        }

        if(state < 1)
            throw new Exception("Malformed Question");

        Number1 = Float.parseFloat(num1);
        Operator = setOperator(op);
        Number2 = Float.parseFloat(num2);

        if(ans != "")
            Answer = Float.parseFloat(ans);
    }

    public String getOperator(){
        switch (Operator) {
            case (ADD):
                return "+";
            case (SUB):
                return "-";
            case (MUL):
                return "*";
            case (DIV):
                return "/";
            default:
                return "No Op";
        }
    }

    public int setOperator(char str){
        switch (str) {
            case ('+'):
                return ADD;
            case ('-'):
                return SUB;
            case('*'):
                return MUL;
            case('/'):
                return DIV;
            default:
                return 0;
        }
    }

    public float getAnswer() {
        switch (Operator) {
            case (ADD):
                return Number1 + Number2;
            case (SUB):
                return Number1 - Number2;
            case (MUL):
                return Number1 * Number2;
            case (DIV):
                return Number1 / Number2;
        }
        return -1;
    }
}
