namespace DrawTopology.Wpf;

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;

[DebuggerDisplay("LineItem: {节点编号} => {目标节点编号}")]
public partial class LineItem : INotifyPropertyChanged
{
    public UInt16 节点编号 { get; set; }
    public UInt16 目标节点编号 { get; set; }

    // 表示未连接
    public const UInt16 未连接 = UInt16.MaxValue;
}

public static class LineItemTestData
{
    public static IEnumerable<LineItem> DefaultData()
    {
        //return new List<LineItem>()
        //{
        //    // 1 => 2 => 3 => 1 环形连接
        //    new() { 节点编号 = 1, 目标节点编号 = 2 },
        //    new() { 节点编号 = 2, 目标节点编号 = 3 },
        //    new() { 节点编号 = 3, 目标节点编号 = 1 },
        //    // 4 => 5 单向连接
        //    new() { 节点编号 = 4, 目标节点编号 = 5 },
        //    new() { 节点编号 = 4, 目标节点编号 = 5 },
        //    // 6 无连接(没有连接也要给，不然不知道)
        //    new() { 节点编号 = 6, 目标节点编号 = LineItem.未连接 },
        //};
        return new List<LineItem>()
        {
            new() { 节点编号 = 0, 目标节点编号 = 1 },
            new() { 节点编号 = 1, 目标节点编号 = 3 },
            new() { 节点编号 = 3, 目标节点编号 = 4 },
            new() { 节点编号 = 4, 目标节点编号 = 0 },
        };
    }

    public static IEnumerable<LineItem> RandomData(int nodeCount, int edgeCount, bool allowLinkSelf)
    {
        var list = new List<LineItem>();
        for (int i = 0; i < nodeCount; i++)
        {
            var id = (UInt16)i;
            list.Add(new() { 节点编号 = id, 目标节点编号 = LineItem.未连接 });
        }
        var r = new Random();
        for (int i = 0; i < edgeCount; i++)
        {
            var from = (UInt16)r.Next(0, nodeCount);
            var to = (UInt16)r.Next(0, nodeCount);
            if (allowLinkSelf || from != to)
            {
                list.Add(new() { 节点编号 = from, 目标节点编号 = to });
            }
            else
            {
                for (int j = 0; from == to && j < 100; j++)
                {
                    to = (UInt16)r.Next(0, nodeCount);
                }
                if (from != to)
                {
                    list.Add(new() { 节点编号 = from, 目标节点编号 = to });
                }
            }
        }
        return list;
    }
}
