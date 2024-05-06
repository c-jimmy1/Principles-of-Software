method loopysqrt(n:int) returns (root:int)
    ensures root * root <= n || n < 0
{
    if (n < 0) {
        return 0;
    }
    root := 0;
    var a := n;
    while (a > 0)
        invariant n == root * root + a && a >= 0
        decreases a
        {
            if (0 > a - (2 * (root + 1) - 1)) {
                break;
            }
            
        root := root + 1;
        a := a - (2 * root - 1);
        }
}

// method Main() {

//     // var n := loopysqrt(4);
//     // print n, "\n"; // 2
    
//     // var m := loopysqrt(25);
//     // print m, "\n"; // 5

//     var o := loopysqrt(47);
//     print o, "\n"; // 7

//     var p := loopysqrt(10);
//     print p, "\n"; // 3

//     var q := loopysqrt(15);
//     print q, "\n"; // 3

//     var r := loopysqrt(17);
//     print r, "\n"; // 4
    
// }