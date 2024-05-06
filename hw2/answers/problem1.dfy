method sumn(n: int) returns (t: int) 
    requires n >= 0
    ensures t == n * (n + 1) / 2
{
    var i := 0;
    t := 0;
    while (i < n) 
        invariant t == i * (i + 1) / 2 && i <= n
        decreases n - i
    {
        i := i + 1;
        t := t + i;
    }
}

// method Main() {
//     var a := sum(5);
//     print a, "\n";

//     var b := sum(10);
//     print b, "\n";

//     var c := sum(100);
//     print c, "\n";
// }
