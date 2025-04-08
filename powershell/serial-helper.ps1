# set-executionpolicy remotesigned
using namespace System.Windows.Forms
using namespace System.IO.Ports
using namespace System.Drawing

[Reflection.Assembly]::LoadWithPartialName("System.Windows.Forms") | Out-Null

$form = [Form]::new()

$serialPort = [SerialPort]::new()
$dataReceived = [powershell]::Create()

$cbPort = [ComboBox]::new()
$btnOpen = [Button]::new()
$btnSwitchLED1 = [Button]::new()
$btnSwitchLED2 = [Button]::new()
$lbSensor1 = [Label]::new()
$lbSensor2 = [Label]::new()
$lbSensor3 = [Label]::new()
$tbView = [TextBox]::new()

$form.Text = "串口助手"

$serialPort.BaudRate = 9600
# $serialPort.NewLine = "`r`n"
# $serialPort.Encoding = [System.Text.Encoding]::ASCII

$btnOpen.Text = "打开串口"
$btnOpen.Location = [Point]::new(120, 0)

$btnSwitchLED1.Location = [Point]::new(120, 30)
$btnSwitchLED1.Text = "切换S1"
$btnSwitchLED1.Enabled = $false

$btnSwitchLED2.Location = [Point]::new(120, 90)
$btnSwitchLED2.Text = "切换S2"
$btnSwitchLED2.Enabled = $false

$lbSensor1.Location = [Point]::new(0, 30)
$lbSensor1.Text = "加载中..."

$lbSensor2.Location = [Point]::new(0, 60)
$lbSensor2.Text = "加载中..."

$lbSensor3.Location = [Point]::new(0, 90)
$lbSensor3.Text = "加载中..."

$tbView.Location = [Point]::new(0, 120);
$tbView.Size = [Size]::new(300, 140);
$tbView.Multiline = $true;
$tbView.ScrollBars = [ScrollBars]::Vertical;

$form.Controls.Add($cbPort)
$form.Controls.Add($btnOpen)
$form.Controls.Add($btnSwitchLED1)
$form.Controls.Add($btnSwitchLED2)
$form.Controls.Add($lbSensor1)
$form.Controls.Add($lbSensor2)
$form.Controls.Add($lbSensor3)
$form.Controls.Add($tbView)

$form.StartPosition = [FormStartPosition]::CenterScreen
# $form.ClientSize = [Size]::new(960, 540)
# $form.Font = [Font]::new("Microsoft YaHei UI", 10, [FontStyle]::Regular, [GraphicsUnit]::Point);

$form.Add_FormClosing({
    try {
        $dataReceived.Stop()
        $dataReceived.Dispose()
        $serialPort.Close()
    } catch {

    }
})

$cbPort.Add_MouseEnter({
    $cbPort.DataSource = [SerialPort]::GetPortNames()
})

$btnOpen.Add_MouseClick({
    if ($btnOpen.Text -eq "打开串口") {
        $serialPort.PortName = $cbPort.SelectedItem
        $serialPort.Open()
        $cbPort.Enabled = $false
        $btnSwitchLED1.Enabled = $true
        $btnSwitchLED2.Enabled = $true
        $btnOpen.Text = "关闭串口"
    } else {
        $serialPort.Close()
        $cbPort.Enabled = $true
        $btnSwitchLED1.Enabled = $false
        $btnSwitchLED2.Enabled = $false
        $btnOpen.Text = "打开串口"
    }
})

$btnSwitchLED1.Add_MouseClick({
    $serialPort.Write("S1")
})

$btnSwitchLED2.Add_MouseClick({
    $serialPort.Write("S2")
})

$dataReceived.AddScript({
    param ($serialPort, $lbSensor1, $lbSensor2, $lbSensor3, $tbView)
    while ($true) {
        $mock = $false
        if ($serialPort.IsOpen -or $mock) {
            try {
                $line = ""
                if ($mock) {
                    # 模拟数据
                    sleep 1
                    $r = [Random]::new()
                    if ($r.Next(0, 2) -eq 0) {
                        $line = "1:$($r.Next(20, 30));$($r.Next(55, 70))"
                    } else {
                        $line = "2:$($r.Next(0, 2))"
                    }
                } else {
                    $line = $serialPort.ReadLine()
                }
                $data = $line.Split(":|;")
                if ($data[0] -eq "1") {
                    $lbSensor1.Text = "温度: $($data[1])"
                    $lbSensor2.Text = "湿度: $($data[2])"
                } elseif ($data[0] -eq "2") {
                    $info = "黑暗"
                    if ($data[1] -eq "1") {
                        $info = "明亮"
                    }
                    $lbSensor3.Text = "光照状态: $($info)"
                }
                $tbView.AppendText("$line`r`n")
            } catch {

            }
        }
    }
}).AddArgument($serialPort).AddArgument($lbSensor1).AddArgument($lbSensor2).AddArgument($lbSensor3).AddArgument($tbView).BeginInvoke() | Out-Null

[Application]::Run($form)
