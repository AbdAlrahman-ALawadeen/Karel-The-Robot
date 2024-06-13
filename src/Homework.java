import stanford.karel.SuperKarel;


public class Homework extends SuperKarel {

    private int cnt = 0;
    @Override
    public void run() {
        setBeepersInBag(1000);
        int width = Count();
        int height = Count();
        if((height == 1 && (width == 1 || width == 2)) || (width == 1 && height == 2)){
            if(height == 2){
                turnLeft();
                move();
            }
            else if(width == 2){
                move();
            }
            System.out.println("Can not Divided");
        }
        else if(height == 2 && width == 2){
            diagonal();
        }
        else if(height == 2 || height == 1){
            HEIGHT(height, width);
        }
        else if(width == 1 || width == 2){
            WIDTH(height, width);
        }
        else if(height == width && height % 2 == 0){
            diagonal();
            StraightForward();
            turnLeft();
            diagonal();
            StraightForward();
        }
        else if(height % 2 == 0 && width % 2 == 0){
            StraightForwardEven(width / 2 - 1, false);
            ForwardLeft();
            StraightForwardEven(height / 2 - 1, false);
            ForwardLeft();
        }
        else if(height % 2 == 1 && width % 2 == 1){
            StraightForwardOdd(width / 2, height,false);
            StraightForwardOdd(height / 2, height,false);
        }
        else if(height % 2 == 1 && width % 2 == 0){
            StraightForwardEven(width / 2 - 1, false);
            turnRight();
            StraightForward();
            turnRight();
            StraightForwardOdd(height / 2, height,true);
        }
        else if(height % 2 == 0 && width % 2 == 1) {
            StraightForwardOdd(width / 2, height, false);
            StraightForwardEven(height / 2 - 1, true);
            ForwardLeft();
        }
        cnt = 0;
    }
    public void checkSafe(){
        while(true){
            if(frontIsBlocked()){
                turnLeft();
            }
            else{
                break;
            }
        }
    }
    public int Count(){
        int count = 1;
        while(true){
            if(frontIsBlocked()){
                turnLeft();
                break;
            }
            count++;
            MoveAndCount();
        }
        return count;
    }
    public void CheckAndPut(){
        if(noBeepersPresent())
            putBeeper();
    }
    public void MoveAndCount(){
        move();
        cnt++;
        System.out.println(cnt);
    }
    public void diagonal(){
        while(true){
            if(frontIsBlocked() && leftIsBlocked()){
                CheckAndPut();
                break;
            }
            CheckAndPut();
            MoveAndCount();
            turnLeft();
            MoveAndCount();
            turnRight();
        }
        checkSafe();
    }
    public void ForwardLeft(){
        turnLeft();
        StraightForward();
        turnLeft();
    }
    public void HEIGHT(int height, int width){
        if(width > 6 || (height == 1)){
            int temp = (width - 3) / 4;
            if(height == 1 && width <= 6){
                temp = 1;
            }
            int full = 0;
            int counter = 0;
            boolean toggle = false;
            for(int i = 0; i < width; i++){
                if(counter < temp){
                    if(!frontIsBlocked()){
                        MoveAndCount();
                    }
                    else{
                        turnAround();
                    }
                    counter++;
                }
                else{
                    full++;
                    if(height == 1){
                        CheckAndPut();
                        if(!frontIsBlocked()){
                            MoveAndCount();
                        }
                        else{
                            turnAround();
                            break;
                        }
                    }
                    else{
                        if(!toggle){
                            toggle = true;
                            TopDown();
                        }
                        else{
                            toggle = false;
                            DownTop();
                        }
                    }
                    if(full < 4){
                        counter = 0;
                    }
                }
            }
        }
        else{
            if(width == 6){
                ZigZagHeight(6, true, 1);
                TopDown();
                DownTop();
            }
            else if(width == 5){
                ZigZagHeight(5, true, 1);
                TopDown();
            }
            else if(width == 4){
                ZigZagHeight(4, true, 1);
            }
            else{
                ZigZagHeight(3, true, 1);
                MoveAndCount();
            }
        }
    }
    public void WIDTH(int height, int width){
        if(height > 6 || (width == 1)){
            int temp = (height - 3) / 4;
            int full = 0;
            int counter = 0;
            if(width == 1){
                temp = 1;
            }
            boolean toggle = false;
            turnLeft();
            for(int i = 0; i < height; i++){
                if(counter < temp){
                    if(!frontIsBlocked()){
                        MoveAndCount();
                    }
                    else{
                        turnAround();
                    }
                    counter++;
                }
                else{
                    full++;
                    if(width == 1){
                        CheckAndPut();
                        if(!frontIsBlocked()){
                            MoveAndCount();
                        }
                        else{
                            turnLeft();
                            break;
                        }
                    }
                    else{
                        if(!toggle){
                            toggle = true;
                            RightToLeft();
                        }
                        else{
                            toggle = false;
                            LeftToRight();
                        }
                    }
                    if(full < 4){
                        counter = 0;
                    }
                }
            }
        }
        else{
            if(height == 6){
                ZigZagHeight(6, false, 2);
                RightToLeft();
                LeftToRight();
            }
            else if(height == 5){
                ZigZagHeight(5, false, 2);
                RightToLeft();
            }
            else if(height == 4){
                ZigZagHeight(4, false, 2);
            }
            else{
                ZigZagHeight(3, false, 2);
                MoveAndCount();
            }
        }
    }
    public void RightToLeft(){
        turnRight();
        CheckAndPut();
        MoveAndCount();
        turnLeft();
        CheckAndPut();
        if(frontIsBlocked()){
            turnAround();
        }
        else{
            MoveAndCount();
        }
    }
    public void LeftToRight(){
        turnLeft();
        CheckAndPut();
        MoveAndCount();
        turnRight();
        CheckAndPut();
        if(frontIsBlocked()){
            turnRight();
            MoveAndCount();
            turnAround();
        }
        else{
            MoveAndCount();
        }
    }
    public int init(int distance, int axis){
        int cnt = 3;
        if(distance < 4){
            cnt = 2;
        }
        CheckAndPut();
        if(axis == 2){
            MoveAndCount();
            turnLeft();
            MoveAndCount();
        }
        else{
            turnLeft();
            MoveAndCount();
            turnRight();
            MoveAndCount();
        }
        return cnt;
    }
    public void ZigZagHeight(int width, boolean flag, int axis){
        boolean toggle = flag;
        int cnt = init(width, axis);
        for(int i = 0; i < cnt; i++){
            if(!toggle){
                toggle = true;
                turnLeft();
                CheckAndPut();
                if((width > 4) || i != cnt - 1){
                    MoveAndCount();
                    turnRight();
                    MoveAndCount();
                }
            }
            else{
                toggle = false;
                turnRight();
                CheckAndPut();
                if(i != cnt - 1 || width > 4){
                    MoveAndCount();
                    turnLeft();
                    MoveAndCount();
                }
            }
        }
    }
    public void TopDown(){
        turnLeft();
        CheckAndPut();
        MoveAndCount();
        CheckAndPut();
        turnRight();
        if(frontIsBlocked()){
            turnAround();
        }
        else{
            MoveAndCount();
        }
    }
    public void DownTop(){
        turnRight();
        CheckAndPut();
        MoveAndCount();
        CheckAndPut();
        turnLeft();
        if(frontIsBlocked()){
            turnLeft();
            MoveAndCount();
            turnLeft();
        }
        else{
            MoveAndCount();
        }
    }
    public void StraightForward(){
        while (!frontIsBlocked()){
            MoveAndCount();
        }
    }
    public void StraightForwardPut(){
        while (!frontIsBlocked()) {
            CheckAndPut();
            MoveAndCount();
        }
        CheckAndPut();
    }
    public void StraightForwardEven(int length, boolean even){
        for(int i = 0; i < length; i++){
            MoveAndCount();
        }
        if(!even){
            turnLeft();
            StraightForwardPut();
            turnRight();
            MoveAndCount();
            turnRight();
        }
        else{
            turnRight();
            StraightForwardPut();
            turnLeft();
            MoveAndCount();
            turnLeft();
        }
        StraightForwardPut();
    }
    public void StraightForwardOdd(int length, int height, boolean odd){
        for(int i = 0; i < length; i++){
            MoveAndCount();
        }
        if(!odd){
            turnLeft();
            StraightForwardPut();
            if(height % 2 == 0){
                turnRight();
                StraightForward();
                turnRight();
            }
            else{
                turnLeft();
                StraightForward();
                turnLeft();
            }
        }
        else{
            turnRight();
            StraightForwardPut();
            turnLeft();
            StraightForward();
            turnLeft();
        }
    }
    public void turnRight(){
        turnLeft();
        turnLeft();
        turnLeft();
    }
}
