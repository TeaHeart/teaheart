namespace SimpleAdmin.Wpf.Extensions;

using System;
using System.Collections.Generic;
using System.Linq;

public static class ICollectionExtensions
{
    public static void RemoveIf<T>(this ICollection<T> source, Func<T, bool> predicate)
    {
        _ = source ?? throw new ArgumentNullException(nameof(source));
        _ = predicate ?? throw new ArgumentNullException(nameof(predicate));
        foreach (var item in source.Where(predicate).ToList())
        {
            source.Remove(item);
        }
    }
}
