import { NativeModules } from 'react-native';
const { Facetec } = NativeModules;

export function init(onSuccess, onFail) {
  Facetec.Init(onSuccess, onFail);
}

export function livenessCheck(onSuccess, onFail) {
  Facetec.LivenessCheck(onSuccess, onFail);
}

export function updateLoadingUI(success) {
  Facetec.UpdateLoadingUI(success);
}

export default {
  init,
  livenessCheck,
  updateLoadingUI,
};
