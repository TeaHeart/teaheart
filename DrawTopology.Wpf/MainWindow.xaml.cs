namespace DrawTopology.Wpf;

using System.Windows;

/// <summary>
/// Interaction logic for MainWindow.xaml
/// </summary>
public partial class MainWindow : Window
{
    public MsaglView MsaglView { get; }
    public WpfNativeView WpfNativeView { get; }

    public MainWindow()
    {
        InitializeComponent();
        DataContext = this;
        MsaglView = new MsaglView();
        WpfNativeView = new WpfNativeView();
    }
}
