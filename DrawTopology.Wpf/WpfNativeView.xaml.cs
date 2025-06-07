namespace DrawTopology.Wpf;

using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Shapes;

/// <summary>
/// WpfNativeView.xaml 的交互逻辑
/// </summary>
public partial class WpfNativeView : UserControl
{
    public bool AllowLinkSelf { get; set; } = false;
    public int NodeCount { get; set; } = 4;
    public int EdgeCount { get; set; } = 8;

    public WpfNativeView()
    {
        InitializeComponent();
        DrawTopology(LineItemTestData.DefaultData());
    }

    public void DrawTopology(IEnumerable<LineItem> items)
    {
        GraphView.Children.Clear();
        // 提取所有唯一节点编号
        var nodeIds = items
            .SelectMany(x => new[] { x.节点编号, x.目标节点编号 })
            .Distinct()
            .Where(x => x != LineItem.未连接)
            .OrderBy(x => x)
            .ToList();
        // 定义布局参数
        const double startX = 50;
        const double startY = 50;
        const double verticalSpace = 50;
        const double nodeWidth = 100;
        const double nodeHeight = 40;
        // 创建节点并记录位置
        var nodePositions = new Dictionary<ushort, Point>();
        for (int i = 0; i < nodeIds.Count; i++)
        {
            ushort id = nodeIds[i];
            var y = startY + i * verticalSpace;
            var pos = new Point(startX, y); // 左上角
            nodePositions[id] = pos;
            // 创建节点控件
            var node = new Border
            {
                Width = nodeWidth,
                Height = nodeHeight,
                BorderBrush = Brushes.Black,
                BorderThickness = new Thickness(1),
                Child = new TextBlock
                {
                    Text = $"Node_{id}",
                    HorizontalAlignment = HorizontalAlignment.Center,
                    VerticalAlignment = VerticalAlignment.Center,
                },
            };
            Canvas.SetLeft(node, pos.X);
            Canvas.SetTop(node, pos.Y);
            GraphView.Children.Add(node);
        }
        // 边偏移
        var edgeCount = 0;
        const double arrowSize = 3;
        // 记录每个节点的边数量
        var edgeCounts = items
            .Where(x => x.节点编号 != LineItem.未连接 && x.目标节点编号 != LineItem.未连接)
            .SelectMany(item => new[] { item.节点编号, item.目标节点编号 })
            .GroupBy(x => x)
            .ToDictionary(x => x.Key, x => new int[] { 0, x.Count() });
        // 创建边
        foreach (
            var item in items.Where(x =>
                x.节点编号 != LineItem.未连接 && x.目标节点编号 != LineItem.未连接
            )
        )
        {
            var srcPos = nodePositions[item.节点编号];
            var dstPos = nodePositions[item.目标节点编号];
            // 例如 1 => 2, 2 => 1 循环连接
            // |-----------------|
            // |                 |-----|
            // |      src        |     |
            // |                 |<----|-----|
            // |-----------------|     |     |
            //                         |     |
            // |-----------------|     |     |
            // |                 |<----|     |
            // |      dst        |           |
            // |                 |-----------|
            // |-----------------|
            // 水平偏移=总共已绘制边数量*间隔
            var horizontalOffset = ++edgeCount * 10;
            // 垂直偏移=当前节点已绘制边数量 * (节点高度 / (节点边总数+1))
            var outEdges = edgeCounts[item.节点编号];
            var outOffset = ++outEdges[0] * nodeHeight / (outEdges[1] + 1);
            var inEdges = edgeCounts[item.目标节点编号];
            var inOffset = ++inEdges[0] * nodeHeight / (inEdges[1] + 1);
            // 点计算
            var srcX = srcPos.X + nodeWidth;
            var srcY = srcPos.Y + outOffset;
            var dstX = dstPos.X + nodeWidth;
            var dstY = dstPos.Y + inOffset;
            var points = new Point[]
            {
                // 起点
                new(srcX, srcY),
                // 路径：水平右移 -> 垂直移动 -> 水平左移
                new(srcX + horizontalOffset, srcY),
                new(dstX + horizontalOffset, dstY),
                new(dstX, dstY),
                // 箭头
                new(dstX + arrowSize, dstY + -arrowSize),
                new(dstX + arrowSize, dstY + arrowSize),
                new(dstX, dstY),
            };

            var path = new Path
            {
                Stroke = Brushes.Black,
                StrokeThickness = 1,
                Data = new PathGeometry
                {
                    Figures =
                    {
                        new()
                        {
                            StartPoint = points[0],
                            Segments =
                            {
                                new LineSegment(points[1], true),
                                new LineSegment(points[2], true),
                                new LineSegment(points[3], true),
                                new LineSegment(points[4], true),
                                new LineSegment(points[5], true),
                                new LineSegment(points[6], true),
                            },
                        },
                    },
                },
            };

            GraphView.Children.Add(path);
        }
        // 调整大小使滚动条生效
        double maxX = 0;
        double maxY = 0;
        foreach (var item in GraphView.Children)
        {
            // 处理节点（Border）
            if (item is Border border)
            {
                double left = Canvas.GetLeft(border);
                double top = Canvas.GetTop(border);
                double right = left + border.Width;
                double bottom = top + border.Height;
                maxX = Math.Max(maxX, right);
                maxY = Math.Max(maxY, bottom);
            }
            // 处理边（Path）
            else if (item is Path path && path.Data is PathGeometry geometry)
            {
                var bounds = geometry.Bounds;
                maxX = Math.Max(maxX, bounds.Right);
                maxY = Math.Max(maxY, bounds.Bottom);
            }
        }
        // 设置Canvas尺寸，添加20像素边距
        GraphView.Width = maxX + 20;
        GraphView.Height = maxY + 20;
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
