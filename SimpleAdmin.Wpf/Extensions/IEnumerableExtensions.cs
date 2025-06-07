namespace SimpleAdmin.Wpf.Extensions;

using System;
using System.Collections;
using System.Collections.Generic;

public static class IEnumerableExtensions
{
    public static void ForEach<T>(this IEnumerable<T> items, Action<T> action)
    {
        _ = items ?? throw new ArgumentNullException(nameof(items));
        _ = action ?? throw new ArgumentNullException(nameof(action));
        foreach (var item in items)
        {
            action(item);
        }
    }

    public static void ForEach(this IEnumerable items, Action<object> action)
    {
        _ = items ?? throw new ArgumentNullException(nameof(items));
        _ = action ?? throw new ArgumentNullException(nameof(action));
        foreach (var item in items)
        {
            action(item);
        }
    }
}
