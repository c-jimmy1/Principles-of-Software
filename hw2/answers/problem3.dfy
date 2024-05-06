method differences(arr: array?<int>) returns (diffs: array<int>) 
    requires arr != null && arr.Length > 0
    ensures diffs.Length == arr.Length - 1 && 
            forall k :: 0 <= k < diffs.Length ==> diffs[k] == arr[k + 1] - arr[k]
{
    diffs := new int[arr.Length - 1];
    var a := 0;
    while (a < diffs.Length) 
        invariant 0 <= a <= diffs.Length && 
                  forall k :: 0 <= k < a ==> diffs[k] == arr[k + 1] - arr[k]
        decreases diffs.Length - a
    {
        diffs[a] := arr[a + 1] - arr[a];
        a := a + 1;
    }
}

// method Main() {
//     var arr := new int[5];
//     arr[0] := 1;
//     arr[1] := 5;
//     arr[2] := 10;
//     arr[3] := 16;
//     arr[4] := 23;
//     var diffs := differences(arr);
//     print diffs[0];
//     print diffs[1];
//     print diffs[2];
//     print diffs[3];
// }