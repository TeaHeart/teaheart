namespace SimpleAdmin.Wpf.Services.Messages;

using CommunityToolkit.Mvvm.Messaging.Messages;

public class LogUpdatedMessage : ValueChangedMessage<string>
{
    public LogUpdatedMessage(string value)
        : base(value) { }
}
