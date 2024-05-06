method differences(arr: seq<int>) returns (diffs: seq<int>)
    requires |arr| > 0
    ensures |diffs| == |arr| - 1 &&
            forall k :: 0 <= k < |diffs| ==> diffs[k] == arr[k + 1] - arr[k]
{
    var a := 0;
    var array_diffs := new int[|arr| - 1];
    while a < |arr| - 1
        invariant 0 <= a <= |arr| - 1 &&
                forall k | 0 <= k < a :: array_diffs[k] == arr[k + 1] - arr[k]
        decreases |arr| - 1 - a
    {
        array_diffs[a] := arr[a + 1] - arr[a];
        a := a + 1;
    }
    diffs := array_diffs[..];
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
