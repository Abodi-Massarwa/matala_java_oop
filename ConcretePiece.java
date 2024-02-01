import java.util.ArrayList;

// Modify ConcretePiece class
public abstract class ConcretePiece implements Piece {
    private Player owner;
    private static int STATIC_ID=1;
    private int id;
    private ArrayList<Position> m_positions;
    private int numOfKills;

    public ConcretePiece() {
        // The constructor remains parameterless
        this.id=STATIC_ID;
        STATIC_ID++;
        m_positions=new ArrayList<Position>();
        numOfKills=0;

    }
    public void addPosition(Position p){
        this.m_positions.add(p);
    }
    public ArrayList<Position> getPositionList(){
        return this.m_positions;
    }
    public static void resetID()
    {
        STATIC_ID=1;
    }

    // Add a setter method to set the owner after object creation
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    public int getId() {
        return this.id;
    }

    public int getNumOfKills() {
        return numOfKills;
    }
    public void incrementNumOfKills()
    {
        ++this.numOfKills;
    }
}
