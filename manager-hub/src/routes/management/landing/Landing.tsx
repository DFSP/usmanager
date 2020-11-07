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

import React from 'react';
import MainLayout from '../../../views/mainLayout/MainLayout';
import styles from './Landing.module.css';
import LocationMap from "../../../components/map/LocationMap";
import {loadContainers} from "../../../actions";
import {connect} from "react-redux";
import {ReduxState} from "../../../reducers";
import Dialog from "../../../components/dialogs/Dialog";
import {IContainer} from "../containers/Container";
import {IMarker} from "../../../components/map/Marker";
import {Error} from "../../../components/errors/Error";
import ReactTooltip from "react-tooltip";

interface StateToProps {
    isLoading: boolean;
    error?: string | null;
    containers: { [key: string]: IContainer };
}

interface DispatchToProps {
    loadContainers: () => void;
}

type Props = StateToProps & DispatchToProps;

interface State {
    center: boolean
}

class Landing extends React.Component<Props, State> {

    private reloadData: NodeJS.Timeout | null = null;

    constructor(props: Props) {
        super(props);
        this.state = {center: true};
    }

    public componentDidMount() {
        this.props.loadContainers();
        this.reloadData = setInterval(this.props.loadContainers, 15000);
    }

    public componentWillUnmount() {
        if (this.reloadData) {
            clearTimeout(this.reloadData);
            this.reloadData = null;
        }
    }

    private handleCenter = () =>
        this.setState({center: !this.state.center})

    private getContainersMarkers = (): IMarker[] => {
        const containers: IContainer[] = Object.values(this.props.containers);
        const markers = new Map<String, IMarker>();
        containers.forEach(container => {
            const publicIpAddress = container.publicIpAddress;
            const marker = markers.get(publicIpAddress) || {title: '', label: '', latitude: 0, longitude: 0};
            if (marker.title === '') {
                marker.title += container.coordinates.label + '<br/>' + publicIpAddress + '<br/>';
            }
            marker.title += container.containerId.substr(0, 5) + ' - ' + container.labels['serviceName'] + '<br/>';
            marker.label = publicIpAddress;
            marker.latitude = container.coordinates.latitude;
            marker.longitude = container.coordinates.longitude;
            markers.set(publicIpAddress, marker);
        });
        return Array.from(markers.values());
    }

    public render() {
        const {error} = this.props;
        const {center} = this.state;
        const centerButton =
            <>
                <button className={`btn-floating btn-flat right ${styles.centerButton}`}
                        data-for='tooltip' data-tip='Center' data-place='bottom'
                        onClick={this.handleCenter}
                        type='button'>
                    <i className='material-icons'>center_focus_weak</i>;
                </button>
            </>;
        const map = <>
            {error && <Error message={error}/>}
            {!error && <LocationMap locations={this.getContainersMarkers()} zoomable center={center} hover/>}
        </>;
        return <div className={`${styles.container}`}>
            <div className={`${styles.buttons}`}>
                <button className={`modal-trigger btn-floating btn-flat right`}
                        data-for='tooltip' data-tip='Fullscreen' data-place='bottom'
                        data-target={'fullscreen-modal'}
                        type='button'>
                    <i className='material-icons'>fullscreen</i>
                </button>
                {centerButton}
            </div>
            <MainLayout>
                <Dialog id={'fullscreen-modal'}
                        title={'Microservices dynamic system management'}
                        fullscreen
                        locked
                        footer={false}
                        titleButtons={centerButton}>
                    {map}
                </Dialog>
                {map}
            </MainLayout>
        </div>;
    }

}

function mapStateToProps(state: ReduxState): StateToProps {
    const isLoading = state.entities.containers.isLoadingContainers;
    const error = state.entities.containers.loadContainersError;
    const containers = state.entities.containers.data;
    return {
        isLoading,
        error,
        containers,
    }
}

const mapDispatchToProps: DispatchToProps = {
    loadContainers,
};

export default connect(mapStateToProps, mapDispatchToProps)(Landing);