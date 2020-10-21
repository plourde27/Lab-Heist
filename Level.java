import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.lang.Math.*;

public class Level {
    
    int level = -1;
    int room = -1;
    
    public Level(int l, int r) {
        level = l;
        room = r;
    }
    
    public String[] setLevel() {
        String[][] stage1 = {
            
{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x...a.....b............................x",
"x...?.....?.........xxxxxx.............B",
"x..............xxxxxxxxxxxxx.....x.....B",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 1

{  
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x............................x",
"x............................x",
"x............................x",
"x............................x",
"x............................x",
"x............................C",
"x............................C",
"x............................x",
"x......xx.........x....x.....x",
"x......xxxx..................x",
"A.....xxxxxxx................x",
"A....xxxxxxxxx..x............x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 2

{  
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x............................x",
"x............................x",
"x............................x",
"x............................x",
"x............................x",
"x........xxx.......xxx.......x",
"x.......xxxx.......xxxx......x",
"x......xxxxx.......xxxx......x",
"x......xxxxx.......xxxx......x",
"x.....xxxxxx.......xxxxx.....x",
"B...xxxxxxxx.......xxxxx.....D",
"B..xxxxxxxxx!!!!!!!xxxxxxx...D",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 3

{"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x....c.......a.........................x",
"C....?.........x!!!!!!!!!!!!!!!x.......E",
"C..........xxxxx!!!!!!!!!!!!!!!xxxxx...E",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 4
                          
{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxHHxxxxxxxxxxxxxx",
"x..............................................................................x",
"x..............................................................................x",
"x................................................................x..x..........x",
"x..............................................................................x",
"x........................................................................x.....x",
"x..............................................................................x",
"x.......................................x....x......A.x........................x",
"x............................................xxxxxxxxxx....................x...x",
"x..............................................................................x",
"x.....d...................................x....................................x",
"D.....?................x..............x........................................F",
"D.+.......x..A.........x......A.....x......x!!!!!!!!!!!!!x...A.......x.........F",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 5

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x......x............................x............x",
"G................................................x",
"G...........................A...............b....x",
"xxxxxx...xxxxxxxxxxxxxxxxxxxxxxxxxx...xxxxxxxxx..x",
"x.....xxx..........................xxx...........x",
"x..............................................x.x",
"x.........................................xxxxxxxx",
"x.........................................x......x",
"x.........................................x..x...x",
"x.........x............x.....x............x..x..xx",
"x................x...............x.....x..x..x...x",
"x.....x......................................x...x",
"x............................................x...x",
"xx...........................................xx..x",
"x............................................x...x",
"x............................................x...x",
"x.x!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!x.a..x...x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx..xx",
"x................................................x",
"x................................................x",
"x................................................x",
"x.............................................x..x",
"x................................................x",
"x................................................x",
"x.........................................x......x",
"x................................................x",
"x................................................x",
"x..........................................x.....x",
"x........................x.......................x",
"x........................x......................xx",
"x........................x.....................xxx",
"x........................xxxxxxxxx............xxxx",
"x...e........................................xxxxx",
"E...?.......................................xxxxxx",
"E.......x......A..x...b.x.....A...x!!!!!!!!xxxxxxx",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 6

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x............................x",
"x............................x",
"x............................F",
"x........................a...F",
"x.......................xxxxxx",
"x............................x",
"x............................x",
"x............................x",
"x............................x",
"x..........x.................x",
"x............................x",
"x..................x.........x",
"x............................x",
"x............x...............x",
"x............................x",
"x.....................x......x",
"x............................x",
"x.....x......................x",
"x............................x",
"x...............x............x",
"x........................x...x",
"x............................x",
"x..........x.................x",
"x............................x",
"x.....................x......x",
"x............................x",
"x.............x..............x",
"x............................x",
"x..................x.........x",
"x............................x",
"x............................x",
"x.........x..................x",
"x............................x",
"x..x.........................x",
"x....................x.......x",
"x............................x",
"H..............x.............x",
"H............................x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 7

{
"xxxxxxxxxxxxxxxxxxxIIxxxxxxxxx",
"x............................x",
"x............................x",
"x..................x.........x",
"x.......................x....x",
"x............................x",
"x............................x",
"x.....................xx.....x",
"x...xxxxx....................x",
"x............................x",
"x............................x",
"x............................x",
"x............................x",
"x............................x",
"x............................x",
"x............................x",
"x.......................f....x",
"x.......................?....G",
"x...c...x...A..x.............G",
"xxxxxxxxxxxxxxxxxxxEExxxxxxxxx"
}, //room 8

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxJJxxxxxxxxxxxxx",
"x................................................x",
"x................................................x",
"x....................................x...........x",
"x........................................x...xxx.x",
"x................................................x",
"x................................................x",
"x..........x..A.....x...c........................x",
"x.........xxxxxxxxxxxxxxxxxxx....................x",
"x....x...........................................x",
"x................................................x",
"x............................x..................xx",
"x..x.............................................x",
"x................................................x",
"x................................................x",
"x................................................x",
"xx...............................................x",
"x................................................x",
"x................................................x",
"x....x!!!!!!!!!!!!!!!!!!!!!!!!!!!!!x.............x",
"x....x!!!!!!!!!!!!!!!!!!!!!!!!!!!!!x.............x",
"x.a..x!!!!!!!!!!!!!!!!!!!!!!!!!!!!!x.b.....a.....x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.x",
"x................................................x",
"x................................................x",
"x................................................x",
"x..............g.................................x",
"x..............?.................................x",
"x...+..................x..c......................x",
"xxxxxxxHHxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 9

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x................................................K",
"x................................................K",
"x...............................................xx",
"x.......xx..A.........xxxxxxxxxxxxxx...A....x....x",
"x....x...xxxxxxxxxxxxxx............xxxxxxxxxx....x",
"x................................................x",
"x..x.............................................x",
"x....................................x.......x...x",
"x...........................x....................x",
"x................................................x",
"x................................................x",
"x.........................x......................x",
"x................................................x",
"x................................................x",
"x................................................x",
"x................................................x",
"x................................................x",
"x...........................x....................x",
"x................................................x",
"x................................................x",
"x................................................x",
"x................................................x",
"x.................................x..............x",
"x................................................x",
"x................................................L",
"x................................................L",
"x................................................x",
"x...........c.........c..........................x",
"xxxxxIIxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 10

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x.................................x",
"x.................................x",
"x.................................x",
"x..............x!!!!!!!x..........x",
"x........x.....xxxxxxxxx.....x....x",
"x..ca.............................x",
"x..xx.............................x",
"x.................................x",
"x.................................x",
"x.................................x",
"x.................................x",
"x.................................x",
"x.................................x",
"x.................................x",
"J.................................x",
"J............c....................x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 11

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x......................................M",
"x......................................M",
"x..........................xxxxxxxxxxxxx",
"x......................................x",
"x.....................xxx..............x",
"x..................x...................x",
"x..................x...................x",
"x..................x...................x",
"x...............x..x...................x",
"x..................x...................x",
"x..................x...................x",
"x............xxxxxxx...................x",
"x......................................x",
"x...........x!!!!!!!x...........x......x",
"x...........x!!!!!!!x..................x",
"x...........x!!!!!!!x..................x",
"x...........xxxxxxxxx............x.....x",
"x................................x..x..x",
"x................................x..x..x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.x",
"J......................................x",
"J......................................x",
"x.......b..x...A...................x...x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 12

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x.............x...............x........x",
"L......h......x...............x........N",
"L..+...?......x.......B.......x........N",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 13

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................O",
"x......................................O",
"x.b...xxx..........xx.................xx",
"xxxx.........xx...........xx...........x",
"x..................................x...x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"M......x.........................x.....x",
"M..a.c.x..................B......x.....x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 14

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x......................................x",
"x......................................x",
"x......................................x",
"x......x....xx...............xx........x",
"x......x............xxx...........xx...x",
"x......x...............................x",
"x......x...............................x",
"x......x.............................xxx",
"P......x................xx.............x",
"P......x...............................x",
"xxxxxxxx.........xx....................x",
"x......................................x",
"x......................................x",
"x.............xx.......................x",
"x...............................x......x",
"x....................xxx...............x",
"x....................xxx...............x",
"x....................xxx...............x",
"x....................xxx...............x",
"x....................xxx...............x",
"N........x.............................x",
"N........x.............................x",
"x...c....x............B................x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 15

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x............................x",
"x............................Q",
"x............................Q",
"x...............x..........xxx",
"x...............x............x",
"xx..............x............x",
"xx...........................x",
"xx...........................x",
"xx...........................x",
"xx...........................x",
"xx.....x.....................x",
"x............................x",
"x..................x.........x",
"x............................x",
"x............................x",
"x............................x",
"x.....x......................x",
"x.....x......................x",
"x.....x......................x",
"x.....x......................x",
"x.....x......................x",
"x.....xxxxxxx.........x!!!!!!x",
"x.....x...............x!!!!!!x",
"x.....x...............xxx!!!!x",
"x.......................x!!!!x",
"x.......................x!!!!x",
"x.......................x!!!!x",
"xxxxx...................x!!!!x",
"x.......................x!!!!x",
"x.......................x!!!!x",
"x.......................x!!!!x",
"x.......................xxxxxx",
"x..................x.........x",
"x..................x.........x",
"x..................x.........x",
"x..................x.........x",
"x..................x.........x",
"x..................x.........x",
"x............................x",
"x!!!!!!!!x.............x.....x",
"x!!!!!!!!x.............x.....x",
"x!!!!!!!!x.............x.....x",
"xxxxxxxxxx.............x.....x",
"x............................x",
"x............................x",
"x......xxx...................x",
"x........x...................x",
"x........x...............x!!!x",
"x........x...............xxxxx",
"x........x...................x",
"x....................i.......x",
"x....................?.......O",
"x....c.c.c..a.a.a..........+.O",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 16

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x......................................R",
"x......................................R",
"x.................................xxxxxx",
"x......................................x",
"x......................................x",
"x....................x.................x",
"x....................x.................x",
"x....................x.................x",
"x....................x.................x",
"x....................x.................x",
"x....................x.................x",
"x........x.............................x",
"x........x.............................x",
"x........x.............................x",
"x........x.............................x",
"x........x.............................x",
"x........x.............................x",
"x......................................x",
"x......................................x",
"x......................................x",
"x.............xxx..............xxxxxxxxx",
"x...............xxxxxxxxxxxxxxxx.......x",
"xxxxxxxxxxxxxxx..................x.....x",
"x.............x..................x.....x",
"x.............x.........B........x.....x",
"x.............xxxxxxxxxxxxxxxxxxxx.....x",
"x......................................x",
"x................................xxxxxxx",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................x!!!!!x",
"x................................xxxxxxx",
"x......................................x",
"x.....................x................x",
"x.....................x................x",
"x.....................x................x",
"x..x...................................x",
"x..x...................................x",
"x..x............................xxxxxxxx",
"x..x...................................x",
"x..x...................................x",
"x..x...................................x",
"x..x...................................x",
"x..x...................................x",
"x..x...................................x",
"x...................x..................x",
"x...................x..................x",
"x...................x..................x",
"x...................x..................x",
"x...................x..................x",
"x...................x..................x",
"x...................x..................x",
"x...................x..................x",
"P......................................x",
"P..+...acacacb.........................x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 17

{
"xxxxxxxxxxxxxxxxxxxxxxxxxxx",
"x.........................x",
"x.........................x",
"x.........................x",
"x.........................x",
"x.........................S",
"x.........................S",
"x.....................xxxxx",
"x.........................x",
"x.........................x",
"x.........................x",
"x.........................x",
"x.........................x",
"x..........x.............xx",
"xx.........x..............x",
"x..........x..............x",
"xC.........x..............x",
"x..........x..............x",
"xx.........x..............x",
"x.........................x",
"x.........................x",
"x..x......................x",
"x.........................x",
"x.........................x",
"x........................Cx",
"x.........................x",
"x.........................x",
"x.........................x",
"x.........................x",
"x.......x.................x",
"x.......x.................x",
"x.......x.................x",
"xx......x.................x",
"x.......x.................x",
"xC......x................xx",
"x.........................x",
"xx........................x",
"x.............x...........x",
"xC............x...........x",
"x.............x...........x",
"xx............x..........Cx",
"x.............x...........x",
"xC........................x",
"x.........................x",
"xx........................x",
"x.......................xxx",
"xC........................x",
"x.........................x",
"xx............x...........x",
"x.............x...........x",
"xC............x...........x",
"x.............x..........Cx",
"xx............x...........x",
"x.........................x",
"x.........................x",
"x.........................x",
"x....j....................x",
"Q....?....................x",
"Q.+........a.c.a.c.a.c.b..x",
"xxxxxxxxxxxxxxxxxxxxxxxxxxx"
}, //room 18


                        };
        
        String[][][] totalStages = {stage1};
        
        return totalStages[level][room];
        
    }
}