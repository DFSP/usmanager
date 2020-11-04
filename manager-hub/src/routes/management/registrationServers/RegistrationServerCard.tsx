/*
 * MIT License
 *
 * Copyright (c) 2020 manager
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import React from "react";
import CardItem from "../../../components/list/CardItem";
import Card from "../../../components/cards/Card";
import {IRegistrationServer} from "./RegistrationServer";
import {IContainer} from "../containers/Container";
import BaseComponent from "../../../components/BaseComponent";
import LinkedContextMenuItem from "../../../components/contextmenu/LinkedContextMenuItem";
import {deleteRegistrationServer} from "../../../actions";
import {connect} from "react-redux";

interface State {
    loading: boolean;
}

interface RegistrationServerCardProps {
    registrationServer: IRegistrationServer;
}

interface DispatchToProps {
    deleteRegistrationServer: (registrationServer: IRegistrationServer) => void;
}

type Props = DispatchToProps & RegistrationServerCardProps;

class RegistrationServerCard extends BaseComponent<Props, State> {

    private mounted = false;

    constructor(props: Props) {
        super(props);
        this.state = {
            loading: false
        }
    }

    public componentDidMount(): void {
        this.mounted = true;
    };

    public componentWillUnmount(): void {
        this.mounted = false;
    }

    private onStopSuccess = (registrationServer: IRegistrationServer): void => {
        super.toast(`<span class="green-text">Registration server <b class="white-text">${registrationServer.containerId}</b> successfully stopped</span>`);
        if (this.mounted) {
            this.setState({loading: false});
        }
        this.props.deleteRegistrationServer(registrationServer)
    }

    private onStopFailure = (reason: string, registrationServer: IRegistrationServer): void => {
        super.toast(`Unable to stop registration-server <a href=/registration-servers/${registrationServer.containerId}><b>${registrationServer.containerId}</b></a>`, 10000, reason, true);
        if (this.mounted) {
            this.setState({loading: false});
        }
    }

    private contextMenu = (): JSX.Element[] => {
        const {registrationServer} = this.props;
        return [
            <LinkedContextMenuItem
                option={'View ports'}
                pathname={`/containers/${registrationServer.containerId}`}
                selected={'ports'}
                state={registrationServer}/>,
            <LinkedContextMenuItem
                option={'View labels'}
                pathname={`/containers/${registrationServer.containerId}`}
                selected={'labels'}
                state={registrationServer}/>,
            <LinkedContextMenuItem
                option={'Check logs'}
                pathname={`/containers/${registrationServer.containerId}`}
                selected={'logs'}
                state={registrationServer}/>,
            <LinkedContextMenuItem
                option={'Modify rules'}
                pathname={`/containers/${registrationServer.containerId}`}
                selected={'rules'}
                state={registrationServer}/>,
            <LinkedContextMenuItem
                option={'View generic rules'}
                pathname={`/containers/${registrationServer.containerId}`}
                selected={'genericContainerRules'}
                state={registrationServer}/>,
            <LinkedContextMenuItem
                option={'Modify simulated metrics'}
                pathname={`/containers/${registrationServer.containerId}`}
                selected={'simulatedMetrics'}
                state={registrationServer}/>,
            <LinkedContextMenuItem
                option={'View generic simulated metrics'}
                pathname={`/containers/${registrationServer.containerId}`}
                selected={'genericSimulatedMetrics'}
                state={registrationServer}/>
        ];
    }

    public render() {
        const {registrationServer} = this.props;
        const {loading} = this.state;
        const CardRegistrationServer = Card<IContainer>();
        return <CardRegistrationServer id={`registrationServer-${registrationServer.containerId}`}
                                       title={registrationServer.containerId.toString()}
                                       link={{
                                           to: {
                                               pathname: `/registration-servers/${registrationServer.containerId}`,
                                               state: registrationServer
                                           }
                                       }}
                                       height={'85px'}
                                       margin={'10px 0'}
                                       hoverable
                                       delete={{
                                           textButton: 'Stop',
                                           url: `containers/${registrationServer.containerId}`,
                                           successCallback: this.onStopSuccess,
                                           failureCallback: this.onStopFailure,
                                       }}
                                       loading={loading}
                                       bottomContextMenuItems={this.contextMenu()}>
            <CardItem key={'host'}
                      label={'Host'}
                      value={registrationServer.publicIpAddress}/>
            <CardItem key={'ports'}
                      label={'Ports'}
                      value={`${registrationServer.ports.map(p => `${p.privatePort}:${p.publicPort}`).join('/')}`}/>
        </CardRegistrationServer>
    }

}

const mapDispatchToProps: DispatchToProps = {
    deleteRegistrationServer
};

export default connect(null, mapDispatchToProps)(RegistrationServerCard);