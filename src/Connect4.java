import java.util.Scanner;

/**
 * @author: ______Tang Yachun(25226177)_________
 *
 *          For the instruction of the assignment please refer to the assignment
 *          GitHub.
 * 
 *          Plagiarism is a serious offense and can be easily detected. Please
 *          don't share your code to your classmate even if they are threatening 
 *          you with your friendship. If they don't have the ability to work on 
 *          something that can compile, they would not be able to change your 
 *          code to a state that we can't detect the act of plagiarism. 
 *
 *          If you cannot work out the logic of the assignment, simply contact
 *          us on Discord. The teaching team is more the eager to provide
 *          you help. We can extend your submission due if it is really
 *          necessary. Just please, don't give up.
 * 
 *          Terms for Using AI:
 * 
 *          You are not allowed to use AI tools in the following ways:
 *          1. Generate code for you.
 *          2. Paraphrase codes and turn them into your own code.
 *          3. Explain or translate the given skeleton code to you.
 *          4. Ask AI if your code is correct or not.
 * 
 * 
 *          You can use AI tools in the following ways:
 *          1. Ask AI why your code does not work with the following prompt:
 *              "Please explain why my code does not compile without giving me suggestion
 *                  how to fix my code"
 *          2. Ask AI to give your priority to complete which part of the assignments:
 *               "Which of the methods that should I do next"  
 */
public class Connect4 {

    /**
     * Total number of rows of the game board. Use this constant whenever possible.
     */
    public static final int HEIGHT = 6;
    /**
     * Total number of columns of the game board. Use this constant whenever
     * possible.
     */
    public static final int WIDTH = 8;

    /**
     * Your main program. You don't need to change this part. This has been done for
     * you.
     */
    public static void main(String[] args) {
        new Connect4().runOnce();
    }

    /**
     * Your program entry. There are two lines missing. Please complete the line
     * labeled with TODO. You can, however, write more than two lines to complete
     * the logic required by TODO. You are not allowed to modify any part other
     * than the TODOs.
     */
    void runOnce() {
        // For people who are not familiar with constants - HEIGHT and WIDTH are two
        // constants defined above. These two constants are visible in the entire
        // program. They cannot be further modified, i.e., it is impossible to write
        // HEIGHT = HEIGHT + 1; or WIDTH = 0; anywhere in your code. However, you can
        // use
        // these two constants as a reference, i.e., row = HEIGHT - 1, for example.

        int[][] board = new int[HEIGHT][WIDTH];
        char[] symbols = {'1', '2'};
        int player = 1;
        printBoard(board, symbols);

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!isGameOver(board) && !quit) {
            System.out.println("Player " + player + ", please enter a command. Press 'h' for help");
            char s = scanner.next().charAt(0);
            switch (s) {
                case 'h':
                case 'H':
                    printHelpMenu();
                    break;
                case 'c':
                case 'C':
                    changeSymbol(player, symbols);
                    break;
                case 'q':
                case 'Q':
                    quit = true;
                    System.out.println("Bye~");
                    continue;
                case 'r':
                    restart(board);
                    printBoard(board, symbols);
                    continue;
                default:
                    if (!validate(s, board)) {
                        System.out.println("Wrong input!, please do again");
                        continue;
                    }

                    // convert the char 's' to the integer 'column', with the value 0 to 7
                    int column = (s - 48);// TODO

                    fillBoard(board, column, player);
                    printBoard(board, symbols);
                    if (isGameOver(board)) {
                        System.out.println("Player " + player + ", you win!");
                        break;
                    } else if (checkMate(player, board))
                        System.out.println("Check mate!");
                    else if (check(player, board))
                        System.out.println("Check!");

                    // TODO: after each iteration, change the variable "player" alternatively
                    // between the integers 1 and 2.
                    if (player == 1) player = 2;
                    else player = 1;

            } // end switch
        } // end while
    }

    /**
     * Reset the board to the initial state
     *
     * @param board - the game board array
     */
    void restart(int[][] board) {
        //TODO
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                board[r][c] = 0;
            }
        }

    }

    /**
     * It allows a player to choose a new symbol to represents its chess.
     * This method should ask the player to enter a new symbol so that symbol is not
     * the same as its opponent.
     * Otherwise the player will need to enter it again until they are different.
     *
     * @param player  - the player who is about to change its symbol
     * @param symbols - the symbols array storing the players' symbols.
     */
    void changeSymbol(int player, char[] symbols) {
        //TODO
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a new Symbol you like: ");
        char newSymbol = input.next().charAt(0);
        if (newSymbol  == symbols[1] || newSymbol == symbols[2]){
            System.out.println("Something wrong, plz change another symbol.\nSymbol cannot be the same as opponent's or unchanged!");
        }
        else {
            symbols[player - 1] = newSymbol;
            System.out.println("Symbol changed SUCCESSFUL.");
        }
    }

    /**
     * This method returns true if the player "player" plays immediately, he/she may
     * end the game. This warns the other player to place his/her next block in a
     * correct position.
     *
     * @param player - the player who is about to win if the other player does not
     *               stop him
     * @param board  - the 2D array of the game board.
     * @return true if the player is about to win, false if the player is not.
     */
    boolean check(int player, int[][] board) {
        // TODO
        for (int i=0;i<8;i++) {
            if (board[0][i] != 0)
                continue;
            //copy array
            int[][] model_array = new int[board.length][board[0].length];

            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[r].length; c++) {
                    model_array[r][c] = board[r][c];
                }
            }
            fillBoard(model_array,i,player);

            if (isGameOver(model_array))
                return true;
        }
        return false;
//        for (int r = 0; r < board.length; r++) {
//            for (int c = 0; c < board[r].length; c++) {
//
//                if (board[r][c] != 0 && board[r][c] == player) {
//                    if (r < board.length - 2) {     //上下
//                        if (board[r][c] == board[r + 1][c] && board[r][c] == board[r + 2][c]) {
//                            return true;
//                        }
//                    }
//                    if (c < board[r].length - 3) {     //左右
//                        if (board[r][c] == board[r][c + 1] && board[r][c] == board[r][c + 2]) {
//                            return true;
//                        }
//                    }
//                    if (r < board.length - 3 && c < board[r].length - 3) {
//                        if (board[r][c] == board[r + 1][c + 1] && board[r][c] == board[r + 2][c + 2]) {
//                            return true;
//                        }
//                    }
//                    if (r < board.length - 3 && c > 2) {
//                        if (board[r][c] == board[r + 1][c - 1] && board[r][c] == board[r + 2][c - 2]) {
//                            return true;
//                        }
//                    }
//                }
//            }
//        }
//        return false;
    }

    /**
     * This method is very similar to the method check. However, a check-mate move
     * means no matter how the other player place his/her next block, in the next
     * turn the player can win the game with certain move.
     * <p>
     * A check-mate move must be a check move. Not all check moves are check-mate
     * move.
     *
     * @param player - the player who is about to win no matter what the other
     *               player does
     * @param board  - the 2D array of the game board/
     * @return true if the player is about to win
     */

    boolean checkMate(int player, int[][] board) {
        // TODO
        if (!check(player, board))
            return false;

        for (int i = 0; i < 8; i++) {

            //先定义一个对手
            int modelPlayer = 0;
            if (player == 1) modelPlayer = 2;
            else modelPlayer = 1;

            if (board[0][i] != 0)
                continue;

            //copy array
            int[][] model_array = new int[board.length][board[0].length];

            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[r].length; c++) {
                    model_array[r][c] = board[r][c];
                }
            }

            //对手下棋
            fillBoard(model_array, i, modelPlayer);

            // 如果这样下之后非将军状态，说明被防住了，不是将死
            if (!check(player,model_array))
                return false;

            // 如果这一步直接赢了，玩家直接嗝屁了
            if (isGameOver(model_array))
                return false;
            }
        return true;
        }

//        int possiblePlace = 0;
//
//        for (int r = 0; r < board.length; r++) {
//            for (int c = 0; c < board[r].length; c++) {
//
//                if (board[r][c] != 0 && board[r][c] == player) {
//                    if (r < board.length - 2) {     //上下
//                        if (board[r][c] == board[r + 1][c] && board[r][c] == board[r + 2][c]) {
//                            possiblePlace++;
//                        }
//                    }
//                    if (c < board[r].length - 3) {     //左右
//                        if (board[r][c] == board[r][c + 1] && board[r][c] == board[r][c + 2]) {
//                            possiblePlace++;
//                        }
//                    }
//                    if (r < board.length - 3 && c < board[r].length - 3) {
//                        if (board[r][c] == board[r + 1][c + 1] && board[r][c] == board[r + 2][c + 2]) {
//                            possiblePlace++;
//                        }
//                    }
//                    if (r < board.length - 3 && c > 2) {
//                        if (board[r][c] == board[r + 1][c - 1] && board[r][c] == board[r + 2][c - 2]) {
//                            possiblePlace++;
//                        }
//                    }
//                }
//            }
//        }
//        return (possiblePlace > 1);



    /**
     * Validate if the input is valid. This input should be one of the character
     * '0', '1', '2', '3,' ..., '7'.
     * The column corresponding to that input should not be full.
     *
     * @param input - the character of the column that the block is intended to
     *              place
     * @param board - the game board
     * @return - true if it is valid, false if it is invalid (e.g., '8', 'c', '@',
     * EOT (which has an unicode 4) )
     */
    boolean validate(char input, int[][] board) {
        //TODO
        if ((int) input - 48 < 0 || (int) input - 48 > 7) {
            return false;
        } else {
            if (board[0][(int) input - 48] != 0) {
                return false;
            } else
                return true;
        }
    }

    /**
     * Given the column (in integer) that a player wish to place his/her block,
     * update the gameboard. You may assume that the input has been validated before
     * calling this method, i.e., there always has room to place the block when
     * calling this method.
     *
     * @param board  - the game board
     * @param column - the column that the player want to places its block
     * @param player - 1 or 2, the player.
     */
    void fillBoard(int[][] board, int column, int player) {
        // TODO
        if (board[0][column] == 0) {
            for (int i = 1; i < board.length; i++) {
                if (board[i][column] != 0) {
                    board[i - 1][column] = player;
                    break;
                }
                else if (i == board.length - 1) {
                    board[i][column] = player;
                }
            }
        }
    }

    /**
     * Print the Help Menu. Please try to understand the switch case in runOnce and
     * Provide a one line comment about the purpose of each symbol.
     */
    void printHelpMenu() {
        // TODO
        //boolean FirstTimePrintHM = true;
        //if (FirstTimePrintHM){
        System.out.println("Welcome to this game! Here is the help menu: ");
        System.out.println("----------------------------------------------");
        System.out.println("Any question? ");
        System.out.println("Press the \"H/h\" and here is the help menu");
        System.out.println("How to play?");
        System.out.println("Press the \"number(From 0 to 7)\" of the corresponding\n column to drop your symbol.");
        System.out.println("Alternatively, you can enter special characters to achieve more functions.");
        System.out.println("Don't like your symbol?");
        System.out.println("Press the \"C/c\" to Change your symbol during your turn.");
        System.out.println("Misstep?");
        System.out.println("Press the \"R/r\" to restart, OR...");
        System.out.println("Press the \"Q/q\" to quit, but PLZ ask your friend...");
        System.out.println("Have a nice time.");
        //    FirstTimePrintHM = false;
        //}
        System.out.println("Help Menu Overview: \n----------------------------------------------");
        System.out.printf("%-25s %s%n", "\"H/h\"", "Open the help menu.");
        System.out.printf("%-25s %s%n", "\"C/c\"", "Change your symbol during your turn.");
        System.out.printf("%-25s %s%n", "\"R/r\"", "Restart the game board.");
        System.out.printf("%-25s %s%n", "\"Q/q\"", "Quit from the game.");
        System.out.printf("%-25s %s%n", "\"Number(From 0 to 7)\"", "Drop your symbol into the corresponding column.");
    }

    /**
     * Determine if the game is over. Game is over if and only if one of the player
     * has a connect-4 or the entire gameboard is fully filled.
     *
     * @param board - the game board
     * @return - true if the game is over, false other wise.
     */
    boolean isGameOver(int[][] board) {
        // TODO
        /*压力比较大，所以碎碎念会特别多，应该不影响评分，反正都是机器
        就真的，一点思路都没有，我现在大概，早上四点半开始写到了上午七点半，然后看了半个小时大概emmmmm
        反正现在上午十点四十五分，应该就当大概花了四个小时在这个上面
        别的都写完了，了解清楚自己要干嘛之后立马就很多东西都出来了
        现在的问题是，判断胜负，将军和将死判断我一点都想不出来！最后剩下的这三块是最难写的！
        AI推荐我先处理这个，我想也是，我要先弄清楚判断输赢的代码才有可能判断的明白将军
        现在分析一下，一共分为四种情况讨论：上下，左右，正四十五度角，负四十五度角
        假设最中间是board[r][c]:
        我们就从上下说明，那么就是取出r-2,r-1,r,r+1(第一组)r-1,r,r+1,r+2(第二组)
        如果全部都是1或者2的话就赢了，但是还要考虑撞墙的问题，比如第一行就只会有r,r+1,r+2,r+3,r+4……
        等等！我是不是应该换个方向……
        最上面不一定会出现，但是最底下的只要放了就一定会出现
        所以rows应该从下往上判断，从board.length-1开始
        创建两个array分别存放两组数据……好像也不是很好
        反正肯定要遍历，干脆动手吧
         */
        boolean isOvered = false;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] != 0) {
                    //我自己写的都要笑自己，能跑但if都堆成小山了
                    /*if (r > 1 && r < board.length - 2) {
                        if (board[r][c] == board[r - 1][c] && board[r][c] == board[r + 1][c])    //↑ ↓
                            if (board[r][c] == board[r - 2][c] || board[r][c] == board[r + 2][c]) {
                                return true;
                            }
                    }

                    if (c > 1 && c < board[r].length - 2) {
                        if (board[r][c] == board[r][c - 1] && board[r][c] == board[r][c + 1])    //← →
                            if (board[r][c] == board[r][c - 2] || board[r][c] == board[r][c + 2]) {
                                return true;
                            }
                    }

                    if (c > 1 && c < board[r].length - 2 && r > 1 && r < board.length - 2) {
                        if (board[r][c] == board[r - 1][c - 1] && board[r][c] == board[r + 1][c + 1])    //↙ ↗
                            if (board[r][c] == board[r - 2][c - 2] || board[r][c] == board[r + 2][c + 2]) {
                                return true;
                            }

                        if (board[r][c] == board[r - 1][c + 1] && board[r][c] == board[r + 1][c - 1])    //↖ ↘
                            if (board[r][c] == board[r - 2][c + 2] || board[r][c] == board[r + 2][c - 2]) {
                                return true;
                     */
                    if (r < board.length - 3) {     //上下
                        if (board[r][c] == board[r + 1][c] && board[r][c] == board[r + 2][c] && board[r][c] == board[r + 3][c]) {
                            return true;
                        }
                    }
                    if (c < board[r].length - 3) {     //左右
                        if (board[r][c] == board[r][c + 1] && board[r][c] == board[r][c + 2] && board[r][c] == board[r][c + 3]) {
                            return true;
                        }
                    }
                    if (r < board.length - 3 && c < board[r].length - 3) {
                        if (board[r][c] == board[r + 1][c + 1] && board[r][c] == board[r + 2][c + 2] && board[r][c] == board[r + 3][c + 3]) {
                            return true;
                        }
                    }
                    if (r < board.length - 3 && c > 2) {
                        if (board[r][c] == board[r + 1][c - 1] && board[r][c] == board[r + 2][c - 2] && board[r][c] == board[r + 3][c - 3]) {
                            return true;
                        }
                    }
                }
            }
        }
        boolean full = true;
        for (int c = 0; c < WIDTH; c++) {
            if (board[0][c] == 0) {
                full = false;
                break;
            }
        }
        if (full)
            return true;
        else
            return false;
    }


    /**
     * Print the game board in a particular format. The instruction can be referred
     * to the GitHub or the demo program. By default, Player 1 uses the character
     * '1' to represent its block. Player 2 uses the character '2'. They can be
     * overrided by the value of symbols array. This method does not change the
     * value of the gameboard nor the symbols array.
     *
     * @param board   - the game board to be printed.
     * @param symbols - the symbols that represents player 1 and player 2.
     */
    void printBoard(int[][] board, char[] symbols) {
        // TODO
        System.out.println(" 0 1 2 3 4 5 6 7 ");
        System.out.println("----------------");
        for (int r = 0; r < board.length; r++) {
            for (int c : board[r]) {
                switch (c) {
                    case 0:
                        System.out.print("|" + " ");
                        break;
                    case 1:
                        System.out.print("|" + symbols[0]);
                        break;
                    case 2:
                        System.out.print("|" + symbols[1]);
                        break;
                }
            }
            System.out.println("|");
        }
        System.out.println("----------------");
    }

}