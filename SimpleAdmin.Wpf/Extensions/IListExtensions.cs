namespace SimpleAdmin.Wpf.Extensions;

using System;
using System.Collections.Generic;

public static class IListExtensions
{
    public static int FindIndex<T>(this IList<T> source, Func<T, bool> predicate)
    {
        _ = source ?? throw new ArgumentNullException(nameof(source));
        _ = predicate ?? throw new ArgumentNullException(nameof(predicate));
        for (int i = 0; i < source.Count; i++)
        {
            if (predicate(source[i]))
            {
                return i;
            }
        }
        return -1;
    }

    public static int FindLastIndex<T>(this IList<T> source, Func<T, bool> predicate)
    {
        _ = source ?? throw new ArgumentNullException(nameof(source));
        _ = predicate ?? throw new ArgumentNullException(nameof(predicate));
        for (int i = source.Count - 1; i >= 0; i--)
        {
            if (predicate(source[i]))
            {
                return i;
            }
        }
        return -1;
    }
}
