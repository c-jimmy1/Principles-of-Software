method dutch(arr: array?<char>) returns (k: int)
    requires arr != null
    // requires forall m :: 0 <= m < arr.Length ==> arr[m] in {'r', 'b'}

    ensures 0 <= k <= arr.Length
    ensures forall i :: 0 <= i < k ==> arr[i] == 'r'
    ensures forall i :: k <= i < arr.Length ==> arr[i] == 'b'
    modifies arr 
{
    var i := 0;
    var j := arr.Length;

    {
        while (i < j)
            invariant i <= j
            invariant 0 <= i <= arr.Length
            invariant 0 <= j <= arr.Length
            // invariant forall m :: 0 <= m < arr.Length ==> arr[m] in {'r', 'b'}
            invariant forall m :: 0 <= m < i ==> arr[m] == 'r'
            invariant forall m :: j <= m < arr.Length ==> arr[m] == 'b'

            decreases j - i
        {
            if(arr[i] == 'r')
            {
                i := i + 1;
            }
            else
            {
                arr[i] := arr[j-1];
                arr[j-1] := 'b';
                j := j - 1;
            }

        }
        k := i;
    }
}


// method Main() 
// {

//     var v := new char[8];
//     v[0] := 'r';
//     v[1] := 'b';
//     v[2] := 'b';
//     v[3] := 'r';
//     v[4] := 'b';
//     v[5] := 'r';
//     v[6] := 'b';
//     v[7] := 'b';


//     var k := dutch(v);

//     print v[0], "\n";
//     print v[1], "\n";
//     print v[2], "\n";
//     print v[3], "\n";
//     print v[4], "\n";
//     print v[5], "\n";
//     print v[6], "\n";
//     print v[7], "\n";

//     print k;


// }