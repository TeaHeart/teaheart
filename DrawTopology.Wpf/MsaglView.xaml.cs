namespace DrawTopology.Wpf;

using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Controls;
using System.Windows.Forms.Integration;
using System.Windows.Input;
using Microsoft.Msagl.Core.Routing;
using Microsoft.Msagl.Drawing;
using Microsoft.Msagl.GraphViewerGdi;

/// <summary>
/// MsaglView.xaml 的交互逻辑
/// </summary>
public partial class MsaglView : UserControl
{
    public bool AllowEdit { get; set; } = false;
    public bool AllowToolBar { get; set; } = false;
    public bool IsUpDown { get; set; } = true;
    public bool IsRectilinear { get; set; } = true;
    public bool AllowLinkSelf { get; set; } = false;
    public int NodeCount { get; set; } = 4;
    public int EdgeCount { get; set; } = 8;

    public MsaglView()
    {
        InitializeComponent();
        DrawTopology(LineItemTestData.DefaultData());
    }

    public void DrawTopology(IEnumerable<LineItem> items)
    {
        var graph = new Graph()
        {
            LayoutAlgorithmSettings = new Microsoft.Msagl.Layout.Layered.SugiyamaLayoutSettings
            {
                EdgeRoutingSettings =
                {
                    EdgeRoutingMode = IsRectilinear
                        ? EdgeRoutingMode.Rectilinear
                        : EdgeRoutingMode.SugiyamaSplines,
                },
            },
            Attr = { LayerDirection = LayerDirection.TB },
        };

        var lookup = new SortedDictionary<UInt16, Node>(
            items
                .SelectMany(x => new[] { x.节点编号, x.目标节点编号 })
                .Distinct()
                .Where(x => x != LineItem.未连接)
                .OrderBy(x => x)
                .ToDictionary(x => x, x => new Node($"Node_{x}"))
        );

        // 节点上下约束
        if (IsUpDown)
        {
            graph.LayerConstraints.AddUpDownVerticalConstraints(lookup.Values.ToArray());
        }

        foreach (var item in lookup)
        {
            if (item.Key != LineItem.未连接)
            {
                var node = item.Value;
                //关闭圆角
                node.Attr.XRadius = 0;
                node.Attr.YRadius = 0;
                graph.AddNode(node);
            }
        }

        foreach (var item in items)
        {
            if (item.节点编号 != LineItem.未连接 && item.目标节点编号 != LineItem.未连接)
            {
                var source = lookup[item.节点编号];
                var target = lookup[item.目标节点编号];
                var edge = graph.AddEdge(source.Id, target.Id);
            }
        }

        var viewer = new GViewer()
        {
            Graph = graph,
            LayoutEditingEnabled = AllowEdit, // 禁用编辑
            ToolBarIsVisible = AllowToolBar, // 关闭工具栏
        };
        var host = new WindowsFormsHost { Child = viewer };

        if (GraphView.Content is IDisposable c)
        {
            c.Dispose();
        }

        GraphView.Content = host;
    }

    private void ListBoxItem_PreviewMouseLeftButtonDown(object sender, MouseButtonEventArgs e)
    {
        DrawTopology(LineItemTestData.DefaultData());
    }

    private void ListBoxItem_PreviewMouseLeftButtonDown_1(object sender, MouseButtonEventArgs e)
    {
        DrawTopology(LineItemTestData.RandomData(NodeCount, EdgeCount, AllowLinkSelf));
    }
}
