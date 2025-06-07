namespace SimpleAdmin.Wpf.Views;

using System.Windows.Controls;

public partial class LogView : UserControl
{
    public LogView()
    {
        InitializeComponent();
    }

    #region 日志自动滚动
    private bool autoScrollEnd = true;

    private void TextBox_TextChanged(object sender, TextChangedEventArgs e)
    {
        if (sender is TextBox textBox && autoScrollEnd)
        {
            textBox.ScrollToEnd();
        }
    }

    private void TextBox_ScrollChanged(object sender, ScrollChangedEventArgs e)
    {
        if (sender is TextBox textBox)
        {
            if (e.ExtentHeightChange == 0)
            {
                if (textBox.VerticalOffset == textBox.ExtentHeight - textBox.ViewportHeight)
                {
                    autoScrollEnd = true;
                }
                else
                {
                    autoScrollEnd = false;
                }
            }
        }
    }
    #endregion
}
