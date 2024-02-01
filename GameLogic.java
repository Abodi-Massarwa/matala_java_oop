import java.util.*;

public class GameLogic implements PlayableLogic {
    private static final int BOARD_SIZE = 11;
    private static final int[][] CORNER_POSITIONS = {{0, 0}, {0, 10}, {10, 0}, {10, 10}};
    private ConcretePlayer firstPlayer, secondPlayer, currentPlayer;
    private ConcretePiece[][] board;
    private int[][] stepsBoard;
    private final Stack<CachedEvent> cachedEventHistory = new Stack<>();
    private ArrayList<ConcretePiece> listOfAllPieces;
    private Tile[][] tileBoard;

    class Tile {

        Position position;
        int stepCount;
        ArrayList<ConcretePiece> visitedPieceList;

        public Tile(int col, int row, int stepCount) {
            this.position = new Position(col, row);
            this.stepCount = stepCount;
            visitedPieceList = new ArrayList<>();
        }

        public Tile(Position p, int stepCount) {
            this.position = p;
            this.stepCount = stepCount;
            visitedPieceList = new ArrayList<>();
        }
    }

    public GameLogic() {
        tileBoard = new Tile[11][11];
        stepsBoard = new int[11][11];
        listOfAllPieces = new ArrayList<>();
        initializePlayersAndBoard();
        initializeBoard();
    }

    private void initializePlayersAndBoard() {
        firstPlayer = new ConcretePlayer(true);
        secondPlayer = new ConcretePlayer(false);
        currentPlayer = secondPlayer;
        board = new ConcretePiece[BOARD_SIZE][BOARD_SIZE];
    }

    private void initializeBoard() {
        // Places 13 bright parts
        //TODO we need to save the steps each time a piece is placed . 
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(5, 3)));    // D1
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(4, 4)));  // D2
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(5, 4)));  // D3
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(6, 4)));  // D4
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(3, 5)));  // D5
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(4, 5)));  // D6
        this.listOfAllPieces.add(this.placePieceAtPosition(new King(firstPlayer), new Position(5, 5)));  // D7 (King)
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(6, 5)));  // D8
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(7, 5)));  // D9
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(4, 6)));  // D10
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(5, 6)));  // D11
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(6, 6)));  // D12
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(firstPlayer), new Position(5, 7)));  // D13

        // RESET STATIC ID
        ConcretePiece.resetID();

// Places 24 dark parts
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(3, 0)));   // A1
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(4, 0)));   // A2
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(5, 0)));   // A3
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(6, 0)));   // A4
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(7, 0)));   // A5
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(5, 1)));   // A6
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(0, 3)));   // A7
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(10, 3)));  // A8
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(0, 4)));   // A9
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(10, 4)));  // A10
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(0, 5)));   // A11
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(1, 5)));   // A12
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(9, 5)));   // A13
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(10, 5)));  // A14
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(0, 6)));   // A15
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(10, 6)));  // A16
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(0, 7)));   // A17
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(10, 7)));  // A18
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(5, 9)));   // A19
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(3, 10)));  // A20
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(4, 10)));  // A21
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(5, 10)));  // A22
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(6, 10)));  // A23
        this.listOfAllPieces.add(this.placePieceAtPosition(new Pawn(secondPlayer), new Position(7, 10)));  // A24
      /*  for (Tile[] t:tileBoard)
        {
            for (Tile tt:t){
                if(tt!=null)
                System.out.println(tt.stepCount);
            }
        }*/
//        board[3][5] = new Pawn(firstPlayer);  // D1
//        board[4][4] = new Pawn(firstPlayer);  // D2
//        board[4][5] = new Pawn(firstPlayer);  // D3
//        board[4][6] = new Pawn(firstPlayer);  // D4
//        board[5][3] = new Pawn(firstPlayer);  // D5
//        board[5][4] = new Pawn(firstPlayer);  // D6
//        board[5][5] = new King(firstPlayer);  // D7 (King)
//        board[5][6] = new Pawn(firstPlayer);  // D8
//        board[5][7] = new Pawn(firstPlayer);  // D9
//        board[6][4] = new Pawn(firstPlayer);  // D10
//        board[6][5] = new Pawn(firstPlayer);  // D11
//        board[6][6] = new Pawn(firstPlayer);  // D12
//        board[7][5] = new Pawn(firstPlayer);  // D13
//
//
//        // Places 24 dark parts
//        board[0][3] = new Pawn(secondPlayer);  // A1
//        board[0][4] = new Pawn(secondPlayer);  // A2
//        board[0][5] = new Pawn(secondPlayer);  // A3
//        board[0][6] = new Pawn(secondPlayer);  // A4
//        board[0][7] = new Pawn(secondPlayer);  // A5
//        board[1][5] = new Pawn(secondPlayer);  // A6
//        board[3][0] = new Pawn(secondPlayer);  // A7
//        board[3][10] = new Pawn(secondPlayer); // A8
//        board[4][0] = new Pawn(secondPlayer);  // A9
//        board[4][10] = new Pawn(secondPlayer); // A10
//        board[5][0] = new Pawn(secondPlayer);  // A11
//        board[5][1] = new Pawn(secondPlayer);  // A12
//        board[5][9] = new Pawn(secondPlayer);  // A13
//        board[5][10] = new Pawn(secondPlayer); // A14
//        board[6][0] = new Pawn(secondPlayer);  // A15
//        board[6][10] = new Pawn(secondPlayer); // A16
//        board[7][0] = new Pawn(secondPlayer);  // A17
//        board[7][10] = new Pawn(secondPlayer); // A18
//        board[9][5] = new Pawn(secondPlayer);  // A19
//        board[10][3] = new Pawn(secondPlayer); // A20
//        board[10][4] = new Pawn(secondPlayer); // A21
//        board[10][5] = new Pawn(secondPlayer); // A22
//        board[10][6] = new Pawn(secondPlayer); // A23
//        board[10][7] = new Pawn(secondPlayer); // A24

    }

    public void printStepsAsc(Player winner) {

        ArrayList<ConcretePiece> listOfPieces = new ArrayList<>();
        ArrayList<ConcretePiece> listOfPiecesPlayerOne = new ArrayList<>();
        ArrayList<ConcretePiece> listOfPiecesPlayerTwo = new ArrayList<>();
        fillListOfPieces(listOfPieces);
        Collections.sort(listOfPieces, new Comparator<ConcretePiece>() {
            @Override
            public int compare(ConcretePiece o1, ConcretePiece o2) {
                return Integer.compare(o1.getPositionList().size(), o2.getPositionList().size());
            }
        });
        for (ConcretePiece p : listOfPieces) {
            if (p.getPositionList().size() > 1) {
                if (p.getOwner().isPlayerOne()) {
                    listOfPiecesPlayerOne.add(p);
                } else {
                    listOfPiecesPlayerTwo.add(p);
                }
            }

        }
        if (winner.isPlayerOne()) {
            for (ConcretePiece p : listOfPiecesPlayerOne) {
                if (p.getId() == 7) {
                    System.out.println("K" + p.getId() + ": " + p.getPositionList());
                } else {
                    System.out.println("D" + p.getId() + ": " + p.getPositionList());
                }
            }
            for (ConcretePiece p : listOfPiecesPlayerTwo) {
                System.out.println("A" + p.getId() + ": " + p.getPositionList());
            }

        } else {
            for (ConcretePiece p : listOfPiecesPlayerTwo) {
                System.out.println("A" + p.getId() + ": " + p.getPositionList());
            }
            for (ConcretePiece p : listOfPiecesPlayerOne) {
                if (p.getId() == 7) {
                    System.out.println("K" + p.getId() + ": " + p.getPositionList());
                } else {
                    System.out.println("D" + p.getId() + ": " + p.getPositionList());
                }
            }

        }
    }

    private ConcretePiece placePieceAtPosition(ConcretePiece piece, Position position) {
        //this places the piece also adds the position to m_positions for each piece
        // also increments stepsBoard[col][row]
        this.board[position.getCol()][position.getRow()] = piece;
        if (piece != null) {
            //TODO NEW NEW NEW TEST PLEASE
            Tile currentTile = this.tileBoard[position.getCol()][position.getRow()];
            if (currentTile != null
                    && !(currentTile.visitedPieceList.contains(piece))) {
                currentTile.visitedPieceList.add(piece);
                currentTile.stepCount++;


            } else if (currentTile == null) {
                this.tileBoard[position.getCol()][position.getRow()] = new Tile(position, 0);
                this.tileBoard[position.getCol()][position.getRow()].stepCount++;

            }
            //TODO **************************************
            this.stepsBoard[position.getRow()][position.getCol()] += 1;
            piece.addPosition(position);
            return piece;
        }
        return null;
    }

    @Override
    public boolean move(Position a, Position b) {
        ConcretePiece piece = board[a.getCol()][a.getRow()];
        /*System.out.println("a.getRow():"+a.getRow()+",a.getCol()"+a.getCol());
        System.out.println("b.getRow():"+b.getRow()+",b.getCol()"+b.getCol());*/
        //System.out.println("ID : "+piece.getId()); /// TODO CHECKED
        if (piece == null || piece.getOwner() != currentPlayer || !isValidMove(a, b))
            return false;
        // System.out.println(a.getRow());
        /*System.out.println("a.getRow() = " + a.getRow() + " ,a.getCol() = " + a.getCol());
        System.out.println("b.getRow() = " + b.getRow() + " ,b.getCol() = " + b.getCol());*/
        ///TODO check if vertical or horizontal to loop over the cols/rows and increment the stepBoard[col][row]
        // incrementStepsBoard(a, b);
        //cachedEventHistory.push(new CachedEvent(a, b, piece, null,null ));
        //board[b.getCol()][b.getRow()] = piece;
        placePieceAtPosition(piece, b);
        placePieceAtPosition(null, a);
        //board[a.getCol()][a.getRow()] = null;

        //TODO scan for possible kill of enemy soldier or king perhaps
        // possibly the game would end if king dies or runs away in one of the corners
        // means endGame() should be used inside this function
        enemyScanAndKill(piece, a, b);
        currentPlayer = isSecondPlayerTurn() ? firstPlayer : secondPlayer;
        return true;
    }

    private void incrementStepsBoard(Position a, Position b) {
        ConcretePiece currentPiece = this.board[b.getCol()][b.getRow()];
        Tile currentTile = this.tileBoard[b.getCol()][b.getRow()];
        if (currentTile != null
                && !(currentTile.visitedPieceList.contains(currentPiece))) {
            currentTile.visitedPieceList.add(currentPiece);
            currentTile.stepCount++;


        }
        /*boolean isVertical = a.getCol() == b.getCol();
        if (isVertical) {
            for (int i = Math.min(a.getRow(), b.getRow()) + 1; i < Math.max(a.getRow(), b.getRow()) - 1; i++) {
                // TODO dont include a and b themselvs assuming when placing the piece there is an increment without calling this function
                stepsBoard[a.getCol()][i]++;
                //TODO NEW NEW NEW TEST IT PLEASE
                Tile currentTile = this.tileBoard[a.getCol()][i];
                ConcretePiece currentPiece = this.board[a.getCol()][i];
                if (currentTile != null
                        && !(currentTile.visitedPieceList.contains(currentPiece))) {
                    currentTile.visitedPieceList.add(currentPiece);
                    currentTile.stepCount++;


                } else if (currentTile == null) {
                    this.tileBoard[a.getCol()][i] = new Tile(a.getCol(), i, 0);
                    this.tileBoard[a.getCol()][i].stepCount++;

                }
                //TODO ********************************************************************

            }
        } else {
            for (int i = Math.min(a.getCol(), b.getCol()) + 1; i < Math.max(a.getCol(), b.getCol()) - 1; i++) {
                // TODO dont include a and b themselvs assuming when placing the piece there is an increment without calling this function
                stepsBoard[i][a.getRow()]++;
                //TODO NEW NEW NEW CHECK PLEAE AND TEST
                Tile currentTile = this.tileBoard[i][a.getRow()];
                ConcretePiece currentPiece = this.board[i][a.getRow()];
                if (currentTile != null
                        && !(currentTile.visitedPieceList.contains(currentPiece))) {
                    currentTile.visitedPieceList.add(currentPiece);
                    currentTile.stepCount++;


                } else if (currentTile == null) {
                    this.tileBoard[i][a.getRow()] = new Tile(i, a.getRow(), 0);
                    this.tileBoard[i][a.getRow()].stepCount++;

                }

            }
        }*/
    }

    private void enemyScanAndKill(ConcretePiece concretePiece, Position a, Position b) {
        /**
         * TODO we need to keep in mind , the gui somehow treats cols before rows board[i][j] means col i row j
         *
         */
        /**
         * we will have to look for all the possible scenarios of killing an enemy soldier
         * 1)
         * 2)
         * 3)
         * 4)
         */
        if (!(concretePiece instanceof King)) {
            int j = b.getCol();
            int i = b.getRow();
            ///TODO if instance of pawn , since king doesnt have the ability to participate in killing an enemy
            // we begin with checking for nearby pieces and we ask them to identify (lets hope not as a cat)
            Piece up = this.getPieceAtPosition(new Position(j, i - 1));
            Piece down = this.getPieceAtPosition(new Position(j, i + 1));
            Piece left = this.getPieceAtPosition(new Position(j - 1, i));
            Piece right = this.getPieceAtPosition(new Position(j + 1, i));
            //checking each side for an enemy soldier or an enemy king
            Boolean[] killedEnemyPawn = {false};
            boolean b1 = check_up(up, j, i, concretePiece, a, killedEnemyPawn);
            boolean b2 = check_down(down, j, i, concretePiece, a, killedEnemyPawn);
            boolean b3 = check_left(left, j, i, concretePiece, a, killedEnemyPawn);
            boolean b4 = check_right(right, j, i, concretePiece, a, killedEnemyPawn);
            if (b1 || b2 || b3 || b4) // these functions only return true when the king is dead
            {
                /// king is dead means Attacker wins means second player...
                secondPlayer.incrementWins();
                endGame(this.secondPlayer);
            }
            //TODO check if you didnt kill an enemy soldier then push event without secondA and secondB.
            // for this we need boolean is_killedEnemyPawn
            if (!killedEnemyPawn[0]) {
                cachedEventHistory.push(new CachedEvent(a, b, concretePiece, null, null));
                // there is no need to bring anyone from the dead we only push info of the current piece
            }

        }
        // if a king he cant attack whatsoever,
        // but we need to check i he reached a corner or not
        if (is_corner(b.getCol(), b.getRow())) {
            board[b.getCol()][b.getRow()] = null;
            firstPlayer.incrementWins();
            endGame(this.firstPlayer);
        }
    }

    void endGame(Player winner) {
        printStepsAsc(winner); ///CHECKED ✅✅✅✅✅✅✅✅✅
        printStars(); // prints 75 stars
        printKillCountDesc(winner);////CHECKED ✅✅✅✅✅✅✅✅✅
        printStars();
        printSquaresDesc(winner); ////CHECKED ✅✅✅✅✅✅✅✅✅
        printStars();
        printUniqueTileSteps(winner);////CHECKED ✅✅✅✅✅✅✅✅✅
        printStars();
      /*  for (Tile[] t:tileBoard)
        {
            for (Tile tt:t){
                if(tt!=null)
                    System.out.println(tt.stepCount);
            }
        }*/
        //reset();
        //. .....
        //TODO IMPORTANT INCREMENT THE WINS OF THE PLAYERS
        // TODO chec
    }

    private void printUniqueTileSteps(Player winner) {
        // TODO we need to traverse over the tileBoard and check for non nulls and whoever .stepCount>1 we add to our arraylist remember we're dealing with object of type Tile
        ArrayList<Tile> tileArrayList = new ArrayList<>();
        for (int col = 0; col < BOARD_SIZE; col++) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                Tile currentTile = tileBoard[col][row];
                ConcretePiece currentPiece = board[col][row];
                if ((currentTile != null) && (currentTile.stepCount > 1)) {
                    // ONLY MORE than 1 & non-null
                    if (!tileArrayList.contains(currentTile)) {
                        tileArrayList.add(new Tile(col, row, currentTile.stepCount));
                    }
                }
            }
        }
        // NOW COMES THE SORTING
        Collections.sort(tileArrayList, new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                return -Integer.compare(o1.stepCount, o2.stepCount);
            }
        }.thenComparing(new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                return Integer.compare(o1.position.getCol(), o2.position.getCol());
            }
        }).thenComparing(new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                return Integer.compare(o1.position.getRow(), o2.position.getRow());
            }
        }));

        for (Tile t : tileArrayList) {
            System.out.println(t.position + "" + t.stepCount + " pieces");
        }
    }


//    private void printTileSteps(Player winner) {
//        ArrayList<Tile> tileArrayList = new ArrayList<>();
//        for (int col = 0; col < stepsBoard[0].length; col++) {
//            for (int row = 0; row < stepsBoard.length; row++) {
//                if (stepsBoard[row][col] > 1) {
//                    // ONLY MORE than 1
//                    tileArrayList.add(new Tile(col, row, stepsBoard[row][col]));
//                }
//            }
//        }
//
//        // NOW COMES THE SORTING
//        Collections.sort(tileArrayList, new Comparator<Tile>() {
//            @Override
//            public int compare(Tile o1, Tile o2) {
//                return -Integer.compare(o1.stepCount, o2.stepCount);
//            }
//        }.thenComparing(new Comparator<Tile>() {
//            @Override
//            public int compare(Tile o1, Tile o2) {
//                return Integer.compare(o1.position.getCol(), o2.position.getCol());
//            }
//        }).thenComparing(new Comparator<Tile>() {
//            @Override
//            public int compare(Tile o1, Tile o2) {
//                return Integer.compare(o1.position.getRow(), o2.position.getRow());
//            }
//        }));
//
//    }

    private void printSquaresDesc(Player winner) {
        ArrayList<ConcretePiece> listOfAll = new ArrayList<>();
        fillListOfPieces(listOfAll);
        Collections.sort(listOfAll, new Comparator<ConcretePiece>() {
            @Override
            public int compare(ConcretePiece o1, ConcretePiece o2) {
                return -Integer.compare(calculateSquaresMoved(o1), calculateSquaresMoved(o2));
            }
        }.thenComparing(new Comparator<ConcretePiece>() {
            @Override
            public int compare(ConcretePiece o1, ConcretePiece o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        }));
        for (ConcretePiece cp : listOfAll) {
            if (calculateSquaresMoved(cp) >= 1) {
                if (cp.getOwner().isPlayerOne()) {
                    if (cp.getId() == 7) {
                        System.out.println("K" + cp.getId() + ": " + calculateSquaresMoved(cp) + " squares");
                    } else {
                        System.out.println("D" + cp.getId() + ": " + calculateSquaresMoved(cp) + " squares");
                    }
                } else {
                    System.out.println("A" + cp.getId() + ": " + calculateSquaresMoved(cp) + " squares");
                }
            }
        }

    }

    private void printKillCountDesc(Player winner) {
        ArrayList<ConcretePiece> listOfPieces = new ArrayList<>();
        fillListOfPieces(listOfPieces);
        Collections.sort(listOfPieces, new Comparator<ConcretePiece>() {
                    @Override
                    public int compare(ConcretePiece o1, ConcretePiece o2) {
                        return -Integer.compare(o1.getNumOfKills(), o2.getNumOfKills());
                    }
                }.thenComparing(new Comparator<ConcretePiece>() {
                    @Override
                    public int compare(ConcretePiece o1, ConcretePiece o2) {
                        if (o1.getOwner() == winner && o2.getOwner() != winner) {
                            return -1;
                        } else if (o1.getOwner() != winner && o2.getOwner() == winner) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                })
                //        .reversed()
        );
        for (ConcretePiece cp : listOfPieces) {
            if (cp.getNumOfKills() > 0) {
                if (cp.getOwner().isPlayerOne()) {
                    System.out.println("D" + cp.getId() + ": " + cp.getNumOfKills() + " kills");
                } else {
                    System.out.println("A" + cp.getId() + ": " + cp.getNumOfKills() + " kills");
                }
            }
        }


    }

    private static int calculateSquaresMoved(ConcretePiece piece) {
        int squaresMoved = 0;
        ArrayList<Position> positions = piece.getPositionList();

        // Assuming Position has getX() and getY() methods
        for (int i = 1; i < positions.size(); i++) {
            int x1 = positions.get(i - 1).getCol();
            int y1 = positions.get(i - 1).getRow();
            int x2 = positions.get(i).getCol();
            int y2 = positions.get(i).getRow();

            // Check if the movement is purely horizontal or vertical
            if (x1 != x2 && y1 == y2) {
                squaresMoved += Math.abs(x2 - x1);
            } else if (x1 == x2 && y1 != y2) {
                squaresMoved += Math.abs(y2 - y1);
            } else {
                // Handle any other cases or ignore diagonal movements
            }
        }

        return squaresMoved;
    }


    private void fillListOfPieces(ArrayList<ConcretePiece> listOfPieces) {
        listOfPieces.clear();
        listOfPieces.addAll(this.listOfAllPieces);
    }

    private static void printStars() {
        System.out.println("***************************************************************************");
    }

    private boolean check_right(Piece right, int j, int i, ConcretePiece current, Position oldPosition, Boolean[] killedEnemyPawn) {
        if (right != null && right.getOwner() != this.currentPlayer) {
            /// it's not null and the owner is the other player , means enemy soldier we proceed to check for upup if it's our soldier
            if (!(right instanceof King)) {
                Piece right_right = getPieceAtPosition(new Position(j + 2, i)); // returns null in 2 cases 1.out of bounds 2.no piece
                if ((right_right != null && right_right.getOwner() == this.currentPlayer && !(right_right instanceof King)) || (j + 2 == BOARD_SIZE) || (is_corner(j + 2, i))) {
                    // Ladies and gentlemen es hora de comer
                    cachedEventHistory.push(new CachedEvent(oldPosition, new Position(j, i), current, new Position(j + 1, i), (ConcretePiece) right)); // TODO when killing someone u save his place and him and ur place
                    board[j + 1][i] = null;
                    ((ConcretePiece) current).incrementNumOfKills();
                    killedEnemyPawn[0] = true;
                    return false;
                }

            } else {
                // TODO IS A KING !! 👑
                Piece right_right = getPieceAtPosition(new Position(j + 2, i));
                Piece right_up = getPieceAtPosition(new Position(j + 1, i - 1));
                Piece right_down = getPieceAtPosition(new Position(j + 1, i + 1));
                if ((right_right != null && right_right.getOwner() == this.currentPlayer) && (right_up != null && right_up.getOwner() == this.currentPlayer)
                        && (right_down != null && right_down.getOwner() == this.currentPlayer)) {
                    // if all exist and are friendly soldiers , kill the king bro ...
                    board[j + 1][i] = null;
                    //  ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((j + 2 == BOARD_SIZE) && (right_up != null && right_up.getOwner() == this.currentPlayer)
                        && (right_down != null && right_down.getOwner() == this.currentPlayer)) {
                    board[j + 1][i] = null;
                    // ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((right_right != null && right_right.getOwner() == this.currentPlayer) && (right_up != null && right_up.getOwner() == this.currentPlayer)
                        && (i + 1 == BOARD_SIZE)) {
                    board[j + 1][i] = null;
                    /// ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((right_right != null && right_right.getOwner() == this.currentPlayer) && (i - 1 == -1)
                        && (right_down != null && right_down.getOwner() == this.currentPlayer)) {

                    board[j + 1][i] = null;
                    //((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game
                }
            }
        }
        // cachedEventHistory.push(new CachedEvent(oldPosition, new Position(j,i), current, null, null)); // TODO when killing someone u save his place and him and ur place
        return false;
    }

    private boolean check_left(Piece left, int j, int i, ConcretePiece current, Position oldPosition, Boolean[] killedEnemyPawn) {
        if (left != null && left.getOwner() != this.currentPlayer) {
            /// it's not null and the owner is the other player , means enemy soldier we proceed to check for upup if it's our soldier
            if (!(left instanceof King)) {
                Piece left_left = getPieceAtPosition(new Position(j - 2, i)); // returns null in 2 cases 1.out of bounds 2.no piece
                if ((left_left != null && left_left.getOwner() == this.currentPlayer && !(left_left instanceof King)) || (j - 2 == -1) || (is_corner(j - 2, i))) {
                    // Ladies and gentlemen es hora de comer
                    cachedEventHistory.push(new CachedEvent(oldPosition, new Position(j, i), current, new Position(j - 1, i), (ConcretePiece) left)); // TODO when killing someone u save his place and him and ur place
                    board[j - 1][i] = null;
                    ((ConcretePiece) current).incrementNumOfKills();
                    killedEnemyPawn[0] = true;
                    return false;
                }


            } else {
                // TODO IS A KING !! 👑
                Piece left_left = getPieceAtPosition(new Position(j - 2, i));
                Piece left_up = getPieceAtPosition(new Position(j - 1, i - 1));
                Piece left_down = getPieceAtPosition(new Position(j - 1, i + 1));
                if ((left_left != null && left_left.getOwner() == this.currentPlayer) && (left_up != null && left_up.getOwner() == this.currentPlayer)
                        && (left_down != null && left_down.getOwner() == this.currentPlayer)) {
                    // if all exist and are friendly soldiers , kill the king bro ...
                    board[j - 1][i] = null;
                    // ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((j - 2 == -1) && (left_up != null && left_up.getOwner() == this.currentPlayer)
                        && (left_down != null && left_down.getOwner() == this.currentPlayer)) {
                    board[j - 1][i] = null;
                    // ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((left_left != null && left_left.getOwner() == this.currentPlayer) && (left_up != null && left_up.getOwner() == this.currentPlayer)
                        && (i + 1 == BOARD_SIZE)) {
                    board[j - 1][i] = null;
                    //((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((left_left != null && left_left.getOwner() == this.currentPlayer) && (i - 1 == -1)
                        && (left_down != null && left_down.getOwner() == this.currentPlayer)) {

                    board[j - 1][i] = null;
                    //((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game
                }
            }
        }
        //cachedEventHistory.push(new CachedEvent(oldPosition, new Position(j,i), current, null, null)); // TODO when killing someone u save his place and him and ur place
        return false;
    }

    private boolean check_down(Piece down, int j, int i, ConcretePiece current, Position oldPosition, Boolean[] killedEnemyPawn) {
        if (down != null && down.getOwner() != this.currentPlayer) {
            /// it's not null and the owner is the other player , means enemy soldier we proceed to check for upup if it's our soldier
            if (!(down instanceof King)) {
                Piece down_down = getPieceAtPosition(new Position(j, i + 2)); // returns null in 2 cases 1.out of bounds 2.no piece
                if ((down_down != null && down_down.getOwner() == this.currentPlayer && !(down_down instanceof King)) || (i + 2 == BOARD_SIZE) || (is_corner(j, i + 2))) {
                    // Ladies and gentlemen es hora de comer
                    cachedEventHistory.push(new CachedEvent(oldPosition, new Position(j, i), current, new Position(j, i + 1), (ConcretePiece) down)); // TODO when killing someone u save his place and him and ur place
                    board[j][i + 1] = null;
                    ((ConcretePiece) current).incrementNumOfKills();
                    killedEnemyPawn[0] = true;
                    return false;
                }

            } else {
                // TODO IS A KING !! 👑
                Piece down_down = getPieceAtPosition(new Position(j, i + 2));
                Piece down_left = getPieceAtPosition(new Position(j - 1, i + 1));
                Piece down_right = getPieceAtPosition(new Position(j + 1, i + 1));
                if ((down_down != null && down_down.getOwner() == this.currentPlayer) && (down_left != null && down_left.getOwner() == this.currentPlayer)
                        && (down_right != null && down_right.getOwner() == this.currentPlayer)) {
                    // if all exist and are friendly soldiers , kill the king bro ...
                    board[j][i + 1] = null;
                    //((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((i + 2 == BOARD_SIZE) && (down_left != null && down_left.getOwner() == this.currentPlayer)
                        && (down_right != null && down_right.getOwner() == this.currentPlayer)) {
                    board[j][i + 1] = null;
                    //  ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((down_down != null && down_down.getOwner() == this.currentPlayer) && (down_left != null && down_left.getOwner() == this.currentPlayer)
                        && (j + 1 == BOARD_SIZE)) {
                    board[j][i + 1] = null;
                    // ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((down_down != null && down_down.getOwner() == this.currentPlayer) && (j - 1 == -1)
                        && (down_right != null && down_right.getOwner() == this.currentPlayer)) {

                    board[j][i + 1] = null;
                    // ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game
                }
            }
        }
        //cachedEventHistory.push(new CachedEvent(oldPosition, new Position(j,i), current, null, null)); // TODO when killing someone u save his place and him and ur place

        return false;
    }

    private boolean check_up(Piece up, int j, int i, ConcretePiece current, Position oldPosition, Boolean[] killedEnemyPawn) {
        if (up != null && up.getOwner() != this.currentPlayer) {
            /// it's not null and the owner is the other player , means enemy soldier we proceed to check for upup if it's our soldier
            if (!(up instanceof King)) {
                Piece up_up = getPieceAtPosition(new Position(j, i - 2)); // returns null in 2 cases 1.out of bounds 2.no piece
                if ((up_up != null && up_up.getOwner() == this.currentPlayer && !(up_up instanceof King)) || (i - 2 == -1) || (is_corner(j, i - 2))) {
                    // Ladies and gentlemen es hora de comer
                    cachedEventHistory.push(new CachedEvent(oldPosition, new Position(j, i), current, new Position(j, i - 1), (ConcretePiece) up)); // TODO when killing someone u save his place and him and ur place
                    board[j][i - 1] = null;
                    ((ConcretePiece) current).incrementNumOfKills();
                    killedEnemyPawn[0] = true;
                    return false;
                }

            } else {
                // TODO IS A KING !! 👑
                Piece up_up = getPieceAtPosition(new Position(j, i - 2));
                Piece up_left = getPieceAtPosition(new Position(j - 1, i - 1));
                Piece up_right = getPieceAtPosition(new Position(j + 1, i - 1));
                if ((up_up != null && up_up.getOwner() == this.currentPlayer) && (up_left != null && up_left.getOwner() == this.currentPlayer)
                        && (up_right != null && up_right.getOwner() == this.currentPlayer)) {
                    // if all exist and are friendly soldiers , kill the king bro ...
                    board[j][i - 1] = null;
                    //((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((i - 2 == -1) && (up_left != null && up_left.getOwner() == this.currentPlayer)
                        && (up_right != null && up_right.getOwner() == this.currentPlayer)) {
                    board[j][i - 1] = null;
                    // ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((up_up != null && up_up.getOwner() == this.currentPlayer) && (up_left != null && up_left.getOwner() == this.currentPlayer)
                        && (j + 1 == BOARD_SIZE)) {
                    board[j][i - 1] = null;
                    //  ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game

                } else if ((up_up != null && up_up.getOwner() == this.currentPlayer) && (j - 1 == -1)
                        && (up_right != null && up_right.getOwner() == this.currentPlayer)) {

                    board[j][i - 1] = null;
                    // ((ConcretePiece) current).incrementNumOfKills();
                    return true;
                    // TODO end the game
                }
            }
        }
        //cachedEventHistory.push(new CachedEvent(oldPosition, new Position(j,i), current, null, null)); // TODO when killing someone u save his place and him and ur place
        return false;
    }

    private boolean is_corner(int col, int row) {
        for (int i = 0; i < CORNER_POSITIONS.length; i++) {
            if (CORNER_POSITIONS[i][0] == row && CORNER_POSITIONS[i][1] == col) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidMove(Position a, Position b) {
        // if it's out of bounds then don't do anything
        if (!isValidPosition(a) || !isValidPosition(b))
            return false;

        Piece piece = getPieceAtPosition(a);
        // if it's not our piece or an empty place then don't move it
        if (piece == null || piece.getOwner() != currentPlayer)
            return false;

        // to prevent diagonal move
        int rowDiff = Math.abs(a.getRow() - b.getRow());
        int colDiff = Math.abs(a.getCol() - b.getCol());
        if (rowDiff != 0 && colDiff != 0)
            return false;

        // Check if the destination position is empty
        if (board[b.getCol()][b.getRow()] != null) {
            return false;
        }

        // Check if there is a piece in the way
        if (rowDiff == 0) { // if in same line means we're about to go horizontal lets check for any pawn in between the col_a and col_b
            int minCol = Math.min(a.getCol(), b.getCol());
            int maxCol = Math.max(a.getCol(), b.getCol());
            for (int col = minCol + 1; col < maxCol; col++) {
                if (board[col][a.getRow()] != null) {
                    return false;
                }
            }
        } else { /// means vertical we check for (min-line+1 to max-line-1 for non-null )
            int minRow = Math.min(a.getRow(), b.getRow());
            int maxRow = Math.max(a.getRow(), b.getRow());
            for (int row = minRow + 1; row < maxRow; row++) {
                if (board[a.getCol()][row] != null) {
                    return false;
                }
            }
        }
        // TODO check it doesnt intervene with the opposite coordinates
        // Check if the destination position is one of the corner positions
        for (int[] corner : CORNER_POSITIONS) {
            if (b.getRow() == corner[0] && b.getCol() == corner[1]) {
                // Only allow the king to go and stand in corner positions
                return piece instanceof King;
            }
        }

        return true;
    }


    private boolean isValidPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }


    @Override
    public Piece getPieceAtPosition(Position position) {
        if (isValidPosition(position)) {
            return board[position.getCol()][position.getRow()];
        }
        return null;
    }

    @Override
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    @Override
    public Player getSecondPlayer() {
        return secondPlayer;
    }

    @Override
    public boolean isGameFinished() {
        // Check if the king has reached one of the corner positions
        for (int[] corner : CORNER_POSITIONS) {
            ConcretePiece piece = board[corner[1]][corner[0]];
            if (piece instanceof King) {
                firstPlayer.incrementWins();
                return true;  // King reached a corner, game over
            }
        }/// check if king is still there
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!is_corner(j, i)) {// only check on non corners
                    if (board[j][i] instanceof King) {
                        return false;
                    }
                }
            }
        }
        secondPlayer.incrementWins();
        return true;  // King not found in corner positions, game continues
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return currentPlayer == secondPlayer;
    }

    @Override
    public void reset() {
        // Clear move history
        cachedEventHistory.clear();
        stepsBoard = new int[11][11];
        listOfAllPieces = new ArrayList<>();
        initializePlayersAndBoard();
        initializeBoard();
    }


    @Override
    public void undoLastMove() {
        if (!cachedEventHistory.isEmpty()) {
            CachedEvent lastCachedEvent = cachedEventHistory.pop();
            Position firstA = lastCachedEvent.getFirstA();
            Position firstB = lastCachedEvent.getFirstB();
            Position secondA = lastCachedEvent.getSecondA();
            ConcretePiece pieceA = lastCachedEvent.getFirstPiece();
            //System.out.println(lastCachedEvent.secondPiece);
            if (lastCachedEvent.secondPiece != null) {
                //System.out.println("second piece not null!!!!!!!!!!!!!!");
                // TODO this means first killed second , we need to rereive secondPiece to secondA and firstPiece to firstA
                board[secondA.getCol()][secondA.getRow()] = lastCachedEvent.getSecondPiece(); // getting the man back from death :) RESURRECTED
            }
            // Move the piece back to the original position
            board[firstA.getCol()][firstA.getRow()] = pieceA;
            board[firstB.getCol()][firstB.getRow()] = null;
            //TODO 1) remove position from position list of piece
            // 2 ) stepsBoard[to.getCol()][to.getRow()]
            stepsBoard[firstB.getCol()][firstB.getRow()]--;
            pieceA.getPositionList().removeLast();

            // Switch back to the previous player
            currentPlayer = (currentPlayer == firstPlayer) ? secondPlayer : firstPlayer;
        }
    }

    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    // Inner class to represent a move
    private static class CachedEvent {
        public Position getSecondA() {
            return secondA;
        }

        public ConcretePiece getSecondPiece() {
            return secondPiece;
        }

        private final Position secondA;// for the deceased
        private ConcretePiece secondPiece;
        private final Position firstA;
        private final Position firstB;
        private ConcretePiece firstPiece;

        public CachedEvent(Position firstA, Position firstB, ConcretePiece firstPiece, Position secondA, ConcretePiece secondPiece) {
            this.firstA = firstA;
            this.firstB = firstB;
            this.firstPiece = firstPiece;
            this.secondA = secondA;
            this.secondPiece = secondPiece;
        }

        public Position getFirstA() {
            return firstA;
        }

        public Position getFirstB() {
            return firstB;
        }

        public ConcretePiece getFirstPiece() {
            return firstPiece;
        }
    }
}