namespace SimpleAdmin.Wpf.ViewModels.Messages;

using CommunityToolkit.Mvvm.Messaging.Messages;
using SimpleAdmin.Wpf.ViewModels;

public class AddTabMessage : ValueChangedMessage<TabItemViewModel>
{
    public AddTabMessage(TabItemViewModel value)
        : base(value) { }
}
