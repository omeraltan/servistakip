import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class KlasorTemelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.klasorTemel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  odasiInput: ElementFinder = element(by.css('input#klasor-temel-odasi'));
  dolabiInput: ElementFinder = element(by.css('input#klasor-temel-dolabi'));
  klasoruInput: ElementFinder = element(by.css('input#klasor-temel-klasoru'));
  fihristSirasiInput: ElementFinder = element(by.css('input#klasor-temel-fihristSirasi'));
  servisSelect: ElementFinder = element(by.css('select#klasor-temel-servis'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setOdasiInput(odasi) {
    await this.odasiInput.sendKeys(odasi);
  }

  async getOdasiInput() {
    return this.odasiInput.getAttribute('value');
  }

  async setDolabiInput(dolabi) {
    await this.dolabiInput.sendKeys(dolabi);
  }

  async getDolabiInput() {
    return this.dolabiInput.getAttribute('value');
  }

  async setKlasoruInput(klasoru) {
    await this.klasoruInput.sendKeys(klasoru);
  }

  async getKlasoruInput() {
    return this.klasoruInput.getAttribute('value');
  }

  async setFihristSirasiInput(fihristSirasi) {
    await this.fihristSirasiInput.sendKeys(fihristSirasi);
  }

  async getFihristSirasiInput() {
    return this.fihristSirasiInput.getAttribute('value');
  }

  async servisSelectLastOption() {
    await this.servisSelect.all(by.tagName('option')).last().click();
  }

  async servisSelectOption(option) {
    await this.servisSelect.sendKeys(option);
  }

  getServisSelect() {
    return this.servisSelect;
  }

  async getServisSelectedOption() {
    return this.servisSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setOdasiInput('odasi');
    await waitUntilDisplayed(this.saveButton);
    await this.setDolabiInput('dolabi');
    await waitUntilDisplayed(this.saveButton);
    await this.setKlasoruInput('klasoru');
    await waitUntilDisplayed(this.saveButton);
    await this.setFihristSirasiInput('fihristSirasi');
    await this.servisSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
