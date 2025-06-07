namespace SimpleAdmin.Wpf.Views;

using System.Windows.Controls;

public partial class MenuView : UserControl
{
    public MenuView()
    {
        InitializeComponent();
    }

    private void ListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        if (sender is not ListBox listBox)
        {
            return;
        }

        listBox.SelectedIndex = -1;
    }
}
